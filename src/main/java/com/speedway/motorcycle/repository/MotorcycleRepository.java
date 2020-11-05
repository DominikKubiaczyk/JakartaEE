package com.speedway.motorcycle.repository;

import com.speedway.datastore.DataStore;
import com.speedway.motorcycle.entity.Motorcycle;
import com.speedway.repository.Repository;

import javax.enterprise.context.Dependent;
import javax.inject.Inject;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Dependent
public class MotorcycleRepository implements Repository<Motorcycle, UUID> {

    private DataStore store;

    @Inject
    public MotorcycleRepository(DataStore store) {
        this.store = store;
    }

    @Override
    public Optional<Motorcycle> find(UUID id) {
        return this.store.findMotorcycle(id);
    }

    @Override
    public List<Motorcycle> findAll() {
        return this.store.findAllMotorcycles();
    }

    @Override
    public void create(Motorcycle entity) {
        this.store.createMotorcycle(entity);
    }

    @Override
    public void delete(UUID id) {
        this.store.deleteMotorcycle(id);
    }

    @Override
    public void update(Motorcycle entity) {
        this.store.updateMotorcycle(entity);
    }

    public List<Motorcycle> findMotorcycleByEngine(UUID id){
        return this.store.findMotorcyclesByEngine(id);
    }
}
