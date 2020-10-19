package com.speedway.engine.repository;

import com.speedway.datastore.DataStore;
import com.speedway.engine.entity.EngineType;
import com.speedway.repository.Repository;

import javax.enterprise.context.Dependent;
import javax.inject.Inject;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Dependent
public class EngineTypeRepository implements Repository<EngineType, UUID> {

    private DataStore store;

    @Inject
    public EngineTypeRepository(DataStore store) {
        this.store = store;
    }

    @Override
    public Optional<EngineType> find(UUID id) {
        return this.store.findEngineType(id);
    }

    @Override
    public List<EngineType> findAll() {
        return this.store.findAllEngineTypes();
    }

    public Optional<EngineType> findByName(String name){
        return this.store.findEngineTypeByName(name);
    }

    @Override
    public void create(EngineType entity) {
        this.store.createEngineType(entity);
    }

    @Override
    public void delete(UUID id) {
        this.store.deleteEngineType(id);
    }

    @Override
    public void update(EngineType entity) {
        this.store.updateEngineType(entity);
    }
}
