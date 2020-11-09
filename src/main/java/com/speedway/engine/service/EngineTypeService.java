package com.speedway.engine.service;

import com.speedway.engine.entity.EngineType;
import com.speedway.engine.repository.EngineTypeRepository;
import lombok.NoArgsConstructor;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@ApplicationScoped
@NoArgsConstructor
public class EngineTypeService {

    private EngineTypeRepository repository;

    @Inject
    public EngineTypeService(EngineTypeRepository repository) {
        this.repository = repository;
    }

    public List<EngineType> findAll() {
        return this.repository.findAll();
    }

    public Optional<EngineType> find(UUID id){
        return this.repository.find(id);
    }

    public Optional<EngineType> findByName(String name){
        return this.repository.findByName(name);
    }

    @Transactional
    public void create(EngineType engineType){
        this.repository.create(engineType);
    }

    @Transactional
    public void delete(UUID id){
        this.repository.delete(id);
    }

    @Transactional
    public void update(EngineType engineType){
        EngineType originalEngineType = repository.find(engineType.getId()).orElseThrow();
        this.repository.detach(originalEngineType);
        this.repository.update(engineType);
    }

}
