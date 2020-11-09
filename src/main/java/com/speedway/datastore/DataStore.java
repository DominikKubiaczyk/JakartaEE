package com.speedway.datastore;

import com.speedway.engine.entity.EngineType;
import com.speedway.motorcycle.entity.Motorcycle;
import com.speedway.rider.entity.Rider;
import com.speedway.serialization.CloningUtility;

import lombok.extern.java.Log;

import javax.enterprise.context.ApplicationScoped;
import java.util.*;

@ApplicationScoped
@Log
public class DataStore {

    private Set<Rider> riders = new HashSet<>();

    private Set<EngineType> engineTypes = new HashSet<>();

    private Set<Motorcycle> motorcycles = new HashSet<>();

    public synchronized List<Rider> findAllRiders() {
        return new ArrayList<>(riders);
    }

    public Optional<Rider> findRider(UUID riderId) {
        return riders.stream()
                .filter(rider -> rider.getId().equals(riderId))
                .findFirst()
                .map(CloningUtility::clone);
    }

    public synchronized void createRider(Rider rider) throws IllegalArgumentException {
        findRider(rider.getId()).ifPresentOrElse(
                original -> {
                    throw new IllegalArgumentException(
                            String.format("Rider already exists"));
                },
                () -> riders.add(rider));
    }

    public synchronized void updateRider(Rider rider) throws IllegalArgumentException {
        findRider(rider.getId()).ifPresentOrElse(
                original -> {
                    riders.remove(original);
                    riders.add(rider);
                },
                () -> {
                    throw new IllegalArgumentException(
                            String.format("Cannot update not existing rider!")
                    );
                }
        );
    }
}
