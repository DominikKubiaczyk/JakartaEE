package com.speedway.motorcycle.service;

import com.speedway.engine.repository.EngineTypeRepository;
import com.speedway.motorcycle.entity.Motorcycle;
import com.speedway.motorcycle.repository.MotorcycleRepository;
import lombok.NoArgsConstructor;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@ApplicationScoped
@NoArgsConstructor
public class MotorcycleService {

    private MotorcycleRepository repository;
    private EngineTypeRepository engineTypeRepository;

    @Inject
    public MotorcycleService(MotorcycleRepository repository, EngineTypeRepository engineTypeRepository){
        this.repository = repository;
        this.engineTypeRepository = engineTypeRepository;
    }

    public List<Motorcycle> findAll(){
        return this.repository.findAll();
    }

    public Optional<Motorcycle> find(UUID id){
        return this.repository.find(id);
    }

    @Transactional
    public void create(Motorcycle motorcycle){
        engineTypeRepository.find(motorcycle.getEngineType().getId()).ifPresent(
                engineType -> {
                    engineType.getMotorcycles().add(motorcycle);
                    repository.create(motorcycle);
                }
        );
    }

    @Transactional
    public void delete(UUID id){
        Motorcycle motorcycle = repository.find(id).orElseThrow();
        motorcycle.getEngineType().getMotorcycles().remove(motorcycle);
        this.repository.delete(motorcycle.getId());
    }

    @Transactional
    public void update(Motorcycle motorcycle){
        Motorcycle originalMotorcycle = repository.find(motorcycle.getId()).orElseThrow();
        repository.detach(originalMotorcycle);
        if(checkIfEngineTypeChanged(originalMotorcycle, motorcycle)){
           originalMotorcycle.getEngineType().getMotorcycles().removeIf(
                   motorcycleToRemove -> motorcycleToRemove.getId().equals(motorcycle.getId())
           );
           engineTypeRepository.find(motorcycle.getEngineType().getId()).ifPresent(
                   engineType -> engineType.getMotorcycles().add(motorcycle)
           );
        }
        this.repository.update(motorcycle);
    }

    public List<Motorcycle> findMotorcycleByEngine(UUID id){
        return this.repository.findMotorcycleByEngine(id);
    }

    public boolean checkIfEngineTypeChanged(Motorcycle original, Motorcycle updated){
        if(original.getEngineType().getId().equals(updated.getEngineType().getId())){
            return false;
        }
        return true;
    }
}
