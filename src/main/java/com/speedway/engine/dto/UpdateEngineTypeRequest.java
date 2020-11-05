package com.speedway.engine.dto;

import com.speedway.engine.entity.EngineProducers;
import com.speedway.engine.entity.EngineType;
import lombok.*;

import java.util.function.BiFunction;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@ToString
@EqualsAndHashCode
public class UpdateEngineTypeRequest {
    private int size;
    private EngineProducers producer;
    private String tuner;
    private float maxSpeed;

    public static BiFunction<EngineType, UpdateEngineTypeRequest, EngineType> dtoToMapperUpdater(){
        return (engineType, request) -> {
            engineType.setMaxSpeed(request.getMaxSpeed());
            engineType.setProducer(request.getProducer());
            engineType.setSize(request.getSize());
            engineType.setTuner(request.getTuner());
            return engineType;
        };
    }
}
