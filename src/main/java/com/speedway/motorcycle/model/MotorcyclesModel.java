package com.speedway.motorcycle.model;

import com.speedway.engine.entity.EngineType;

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
public class MotorcyclesModel implements Serializable {

    @Getter
    @Setter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor(access = AccessLevel.PRIVATE)
    @ToString
    @EqualsAndHashCode
    public static class Motorcycle {
        private UUID id;
        private EngineType engineType;
        private String color;
    }

    @Singular
    private List<Motorcycle> motorcycles;

    public static Function<Collection<com.speedway.motorcycle.entity.Motorcycle>, MotorcyclesModel> entityToModelMapper() {
        return motorcycles -> {
            MotorcyclesModel.MotorcyclesModelBuilder model = MotorcyclesModel.builder();
            motorcycles.stream()
                    .map(motorcycle -> Motorcycle.builder()
                            .id(motorcycle.getId())
                            .engineType(motorcycle.getEngineType())
                            .color(motorcycle.getColor())
                            .build())
                    .forEach(model::motorcycle);
            return model.build();
        };
    }

}
