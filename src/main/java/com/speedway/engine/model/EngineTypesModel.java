package com.speedway.engine.model;

import com.speedway.engine.entity.EngineProducers;
import lombok.*;

import java.io.Serializable;
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
public class EngineTypesModel implements Serializable {

    @Getter
    @Setter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor(access = AccessLevel.PRIVATE)
    @ToString
    @EqualsAndHashCode
    public static class EngineType {
        private UUID id;
        private float size;
        private EngineProducers producer;
    }

    @Singular
    private List<EngineType> engineTypes;

    public static Function<Collection<com.speedway.engine.entity.EngineType>, EngineTypesModel> entityToModelMapper() {
        return engineTypes -> {
            EngineTypesModel.EngineTypesModelBuilder model = EngineTypesModel.builder();
            engineTypes.stream()
                    .map(engineType -> EngineType.builder()
                            .id(engineType.getId())
                            .size(engineType.getSize())
                            .producer(engineType.getProducer())
                            .build())
                    .forEach(model::engineType);
            return model.build();
        };
    }

}


