package com.speedway.motorcycle.model;

import com.speedway.engine.entity.EngineType;
import com.speedway.engine.model.EngineTypeModel;
import com.speedway.motorcycle.entity.Motorcycle;
import lombok.*;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.UUID;
import java.util.function.Function;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@ToString
@EqualsAndHashCode
public class MotorcycleCreateModel {
    private EngineTypeModel engineType;
    private String color;
    private float weight;
    private String productionDate;

    public static Function<MotorcycleCreateModel, Motorcycle> modelToEntityMapper(
            Function<String, EngineType> engineTypeFunction
    ){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        return model -> Motorcycle.builder()
                .id(UUID.randomUUID())
                .engineType(engineTypeFunction.apply(String.format("%s %f",model.getEngineType().getProducer().toString(), model.getEngineType().getSize())))
                .color(model.getColor())
                .weight(model.getWeight())
                .productionDate(LocalDate.parse(model.getProductionDate(), formatter))
                .build();
    }
}
