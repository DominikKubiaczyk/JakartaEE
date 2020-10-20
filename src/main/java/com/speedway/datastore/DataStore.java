package com.speedway.datastore;

import com.speedway.engine.entity.EngineType;
import com.speedway.motorcycle.entity.Motorcycle;
import com.speedway.rider.entity.Rider;
import com.speedway.serialization.CloningUtility;
import lombok.extern.java.Log;

import javax.enterprise.context.ApplicationScoped;
import java.lang.invoke.StringConcatException;
import java.util.*;
import java.util.stream.Collectors;

@ApplicationScoped
@Log
public class DataStore {

    private Set<Rider> riders = new HashSet<>();

    private Set<EngineType> engineTypes = new HashSet<>();

    private Set<Motorcycle> motorcycles = new HashSet<>();

    public synchronized List<Rider> findAllRiders() {
        return new ArrayList<>(riders);
    }

    public synchronized List<EngineType> findAllEngineTypes() {return new ArrayList<>(engineTypes);}

    public synchronized List<Motorcycle> findAllMotorcycles() {return new ArrayList<>(motorcycles);}

    public Optional<Rider> findRider(UUID riderId) {
        return riders.stream()
                .filter(rider -> rider.getId().equals(riderId))
                .findFirst()
                .map(CloningUtility::clone);
    }

    public Optional<EngineType> findEngineType(UUID engineId) {
        return engineTypes.stream()
                .filter(engine -> engine.getId().equals(engineId))
                .findFirst()
                .map(CloningUtility::clone);
    }

    public Optional<Motorcycle> findMotorcycle(UUID motorcycleId) {
        return motorcycles.stream()
                .filter(motorcycle -> motorcycle.getId().equals(motorcycleId))
                .findFirst()
                .map(CloningUtility::clone);
    }

    public List<Motorcycle> findMotorcyclesByEngine(UUID id) {
        return motorcycles.stream()
                .filter(motorcycle -> motorcycle.getEngineType().getId().equals(id))
                .map(CloningUtility::clone)
                .collect(Collectors.toList());
    }

    public Optional<EngineType> findEngineTypeByName(String name){
        return engineTypes.stream()
                .filter(engineType -> String.format("%s %s",engineType.getProducer().toString(), Integer.toString(engineType.getSize())).equals(name))
                .findFirst()
                .map(CloningUtility::clone);
    }

    public synchronized void createRider(Rider rider) throws IllegalArgumentException {
        findRider(rider.getId()).ifPresentOrElse(
                original -> {
                    throw new IllegalArgumentException(
                            String.format("Rider juz istnieje"));
                },
                () -> riders.add(rider));
    }

    public synchronized void createEngineType(EngineType engineType) throws IllegalArgumentException {
        findEngineType(engineType.getId()).ifPresentOrElse(
                original -> {
                    throw new IllegalArgumentException(
                            String.format("Engine type already exists")
                    );
                },
                () -> engineTypes.add(engineType)
        );
    }

    public synchronized void createMotorcycle(Motorcycle motorcycle) throws IllegalArgumentException {
        findMotorcycle(motorcycle.getId()).ifPresentOrElse(
                original -> {
                    throw new IllegalArgumentException(
                            String.format("Motorcycle already exists")
                    );
                },
                () -> motorcycles.add(motorcycle)
        );
    }

    public synchronized void updateRider(Rider rider) throws IllegalArgumentException {
        findRider(rider.getId()).ifPresentOrElse(
                original -> {
                    riders.remove(original);
                    riders.add(rider);
                },
                () -> {
                    throw new IllegalArgumentException(
                            String.format("Nie można zaktualizować nieistniejącego ridera!")
                    );
                }
        );
    }

    public synchronized void updateEngineType(EngineType engineType) throws IllegalArgumentException {
        findEngineType(engineType.getId()).ifPresentOrElse(
                original -> {
                    engineTypes.remove(original);
                    engineTypes.add(engineType);
                },
                () -> {
                    throw new IllegalArgumentException(
                            String.format("Cannot update engine type that does not exist!")
                    );
                }
        );
    }

    public synchronized void updateMotorcycle(Motorcycle motorcycle) throws IllegalArgumentException {
        System.out.println("PPPPPPP " + motorcycle);
        findMotorcycle(motorcycle.getId()).ifPresentOrElse(
                original -> {
                    motorcycles.remove(original);
                    motorcycles.add(motorcycle);
                },
                () -> {
                    throw new IllegalArgumentException(
                                String.format("Cannot update motorcycle that does not exist!")
                    );
                }
        );
    }

    public synchronized void deleteEngineType(UUID id) throws IllegalArgumentException{
        findEngineType(id).ifPresentOrElse(
                original -> {
                    motorcycles = motorcycles.stream()
                            .filter(motorcycle -> !motorcycle.getEngineType().equals(original))
                            .collect(Collectors.toSet());
                    engineTypes.remove(original);
                },
                () -> {
                    throw new IllegalArgumentException(
                            String.format("Engine type with id \"%s\" does not exist!", id)
                    );
                }
        );
    }

    public synchronized void deleteMotorcycle(UUID id) throws IllegalArgumentException{
        System.out.println("DeleteStore");
        findMotorcycle(id).ifPresentOrElse(
                original -> motorcycles.remove(original),
                () -> {
                    throw new IllegalArgumentException(
                            String.format("The motorcycle with id \"%s\" does not exist", id)
                    );
                }
        );
    }
}
