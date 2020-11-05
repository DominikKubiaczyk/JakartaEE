package com.speedway.motorcycle.service;

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

    @Inject
    public MotorcycleService(MotorcycleRepository repository){
        this.repository = repository;
    }

    public List<Motorcycle> findAll(){
        return this.repository.findAll();
    }

    public Optional<Motorcycle> find(UUID id){
        return this.repository.find(id);
    }

    @Transactional
    public void create(Motorcycle motorcycle){
        this.repository.create(motorcycle);
    }

    @Transactional
    public void delete(UUID id){
        this.repository.delete(this.repository.find(id).orElseThrow().getId());
    }

    @Transactional
    public void update(Motorcycle motorcycle){
        this.repository.update(motorcycle);
    }

    public List<Motorcycle> findMotorcycleByEngine(UUID id){
        return this.repository.findMotorcycleByEngine(id);
    }
}
