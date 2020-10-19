package com.speedway.motorcycle.model;

import com.speedway.engine.entity.EngineType;
import com.speedway.motorcycle.entity.Motorcycle;
import com.speedway.rider.entity.Rider;
import lombok.*;

import java.time.LocalDate;
import java.util.UUID;
import java.util.function.Function;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@ToString
@EqualsAndHashCode
public class MotorcycleModel {
    private EngineType engineType;
    private String color;
    private float weight;
    private LocalDate productionDate;

    public static Function<Motorcycle, MotorcycleModel> entityToModelMapper() {
        return motorcycle -> MotorcycleModel.builder()
                .color(motorcycle.getColor())
                .engineType(motorcycle.getEngineType())
                .productionDate(motorcycle.getProductionDate())
                .weight(motorcycle.getWeight())
                .build();
    }
}

