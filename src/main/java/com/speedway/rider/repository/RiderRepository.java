package com.speedway.rider.repository;

import com.speedway.datastore.DataStore;
import com.speedway.repository.Repository;
import com.speedway.rider.entity.Rider;

import javax.enterprise.context.Dependent;
import javax.inject.Inject;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Dependent
public class RiderRepository implements Repository<Rider, UUID> {

    private DataStore store;

    @Inject
    public RiderRepository(DataStore store){
        this.store = store;
    }

    @Override
    public Optional<Rider> find(UUID id) {
        return this.store.findRider(id);
    }

    @Override
    public List<Rider> findAll() {
        return this.store.findAllRiders();
    }

    @Override
    public void create(Rider entity) {
        this.store.createRider(entity);
    }

    @Override
    public void delete(UUID id) {
        throw new UnsupportedOperationException("Not implemented!");
    }

    @Override
    public void update(Rider entity) {
        this.store.updateRider(entity);
    }
}
