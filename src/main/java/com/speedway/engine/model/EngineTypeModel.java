package com.speedway.engine.model;

import com.speedway.engine.entity.EngineProducers;
import com.speedway.engine.entity.EngineType;
import lombok.*;

import java.util.function.Function;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@ToString
@EqualsAndHashCode
public class EngineTypeModel {
    private int size;
    private EngineProducers producer;
    private String tuner;
    private float maxSpeed;

    public static Function<EngineType, EngineTypeModel> entityToModelMapper() {
        return engineType -> EngineTypeModel.builder()
                .maxSpeed(engineType.getMaxSpeed())
                .producer(engineType.getProducer())
                .size(engineType.getSize())
                .tuner(engineType.getTuner())
                .build();
    }
}
