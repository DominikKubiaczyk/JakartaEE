package com.speedway.engine.dto;

import com.speedway.engine.entity.EngineProducers;
import com.speedway.engine.entity.EngineType;
import lombok.*;

import java.util.UUID;
import java.util.function.Function;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@ToString
@EqualsAndHashCode
public class CreateEngineTypeRequest {
    private int size;
    private EngineProducers producer;
    private String tuner;
    private float maxSpeed;

    public static Function<CreateEngineTypeRequest, EngineType> dtoToEntityMapper(){
        return request -> EngineType.builder()
                .id(UUID.randomUUID())
                .maxSpeed(request.getMaxSpeed())
                .producer(request.getProducer())
                .size(request.getSize())
                .tuner(request.getTuner())
                .build();
    }
}
