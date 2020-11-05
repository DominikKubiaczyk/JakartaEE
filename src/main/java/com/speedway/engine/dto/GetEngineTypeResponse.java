package com.speedway.engine.dto;

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
public class GetEngineTypeResponse {

    private int size;
    private EngineProducers producer;
    private String tuner;
    private float maxSpeed;

    public static Function<EngineType, GetEngineTypeResponse> entityToDtoMapper(){
        return engineType -> GetEngineTypeResponse.builder()
                .size(engineType.getSize())
                .maxSpeed(engineType.getMaxSpeed())
                .producer(engineType.getProducer())
                .tuner(engineType.getTuner())
                .build();
    }
}
