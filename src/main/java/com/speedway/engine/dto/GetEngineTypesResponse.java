package com.speedway.engine.dto;

import com.speedway.engine.entity.EngineProducers;
import com.speedway.engine.entity.EngineType;
import lombok.*;

import java.util.Collection;
import java.util.List;
import java.util.UUID;
import java.util.function.Function;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@ToString
@EqualsAndHashCode
public class GetEngineTypesResponse {

    @Getter
    @Setter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor(access = AccessLevel.PRIVATE)
    @ToString
    @EqualsAndHashCode
    public static class EngineType {
        private UUID id;
        private EngineProducers producer;
        private int size;
    }

    @Singular
    private List<EngineType> engineTypes;

    public static Function<Collection<com.speedway.engine.entity.EngineType>, GetEngineTypesResponse> entityToDtoMapper(){
        return engTypes -> {
            GetEngineTypesResponseBuilder response = GetEngineTypesResponse.builder();
            engTypes.stream()
                    .map(engineType -> EngineType.builder()
                                .id(engineType.getId())
                                .producer(engineType.getProducer())
                                .size(engineType.getSize())
                                .build())
                    .forEach(response::engineType);
            return response.build();
        };
    }
}
