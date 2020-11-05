package com.speedway.configuration;

import com.speedway.engine.entity.EngineProducers;
import com.speedway.engine.entity.EngineType;
import com.speedway.engine.service.EngineTypeService;
import com.speedway.motorcycle.entity.Motorcycle;
import com.speedway.motorcycle.service.MotorcycleService;
import com.speedway.rider.entity.Rider;
import com.speedway.rider.entity.Role;
import com.speedway.rider.service.RiderService;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.context.Initialized;
import javax.enterprise.context.control.RequestContextController;
import javax.enterprise.event.Observes;
import javax.inject.Inject;
import java.time.LocalDate;
import java.util.UUID;

@ApplicationScoped
public class InitializedData {
    private final RiderService riderService;
    private final MotorcycleService motorcycleService;
    private final EngineTypeService engineTypeService;
    private final RequestContextController requestContextController;

    @Inject
    public InitializedData(RiderService riderService, MotorcycleService motorcycleService, EngineTypeService engineTypeService, RequestContextController requestContextController){
        this.riderService = riderService;
        this.engineTypeService = engineTypeService;
        this.motorcycleService = motorcycleService;
        this.requestContextController = requestContextController;
    }

    public void contextInitialized(@Observes @Initialized(ApplicationScoped.class) Object init){
        init();
    }

    private synchronized void init() {
        requestContextController.activate();
        Rider rider1 = Rider.builder()
                .id(UUID.randomUUID())
                .firstName("Emil")
                .lastName("Sayfutdinov")
                .role(Role.RIDER)
                .startNumber(1)
                .birthDate(LocalDate.of(1995,10,19))
                .build();

        Rider rider2 = Rider.builder()
                .id(UUID.randomUUID())
                .firstName("Chris")
                .lastName("Holder")
                .role(Role.RIDER)
                .startNumber(1)
                .birthDate(LocalDate.of(1996,10,19))
                .build();

        Rider rider3 = Rider.builder()
                .id(UUID.randomUUID())
                .firstName("Jack")
                .lastName("Holder")
                .role(Role.RIDER)
                .startNumber(1)
                .birthDate(LocalDate.of(1997,10,19))
                .build();

        Rider rider4 = Rider.builder()
                .id(UUID.randomUUID())
                .firstName("Dominik")
                .lastName("Kubiaczyk")
                .role(Role.ADMIN)
                .startNumber(1)
                .birthDate(LocalDate.of(1998,10,19))
                .build();

        riderService.create(rider1);
        riderService.create(rider2);
        riderService.create(rider3);
        riderService.create(rider4);

        EngineType engineType1 = EngineType.builder()
                .maxSpeed(110)
                .producer(EngineProducers.GM)
                .size(400)
                .tuner("Ryszard Kowalski")
                .build();

        EngineType engineType2 = EngineType.builder()
                .maxSpeed(115)
                .producer(EngineProducers.GTR)
                .size(500)
                .tuner("Ryszard Kowalski")
                .build();

        EngineType engineType3 = EngineType.builder()
                .maxSpeed(111)
                .producer(EngineProducers.JAWA)
                .size(500)
                .tuner("Ryszard Kowalski")
                .build();

        EngineType engineType4 = EngineType.builder()
                .maxSpeed(110)
                .producer(EngineProducers.GM)
                .size(500)
                .tuner("Ashley Holoway")
                .build();

        engineTypeService.create(engineType1);
        engineTypeService.create(engineType2);
        engineTypeService.create(engineType3);
        engineTypeService.create(engineType4);

        Motorcycle motorcycle1 = Motorcycle.builder()
                .color("Red")
                .engineType(engineType1)
                .productionDate(LocalDate.of(2020,03,01))
                .weight(50)
                .build();

        Motorcycle motorcycle2 = Motorcycle.builder()
                .color("Blue")
                .engineType(engineType1)
                .productionDate(LocalDate.of(2020,03,01))
                .weight(50)
                .build();

        Motorcycle motorcycle3 = Motorcycle.builder()
                .color("White-green")
                .engineType(engineType3)
                .productionDate(LocalDate.of(2020,03,01))
                .weight(50)
                .build();

        Motorcycle motorcycle4 = Motorcycle.builder()
                .color("Yellow")
                .engineType(engineType4)
                .productionDate(LocalDate.of(2020,03,01))
                .weight(50)
                .build();

        motorcycleService.create(motorcycle1);
        motorcycleService.create(motorcycle2);
        motorcycleService.create(motorcycle3);
        motorcycleService.create(motorcycle4);
    }

}
