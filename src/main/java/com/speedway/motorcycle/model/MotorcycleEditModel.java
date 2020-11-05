package com.speedway.motorcycle.model;

import com.speedway.engine.entity.EngineType;
import com.speedway.motorcycle.entity.Motorcycle;
import lombok.*;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.function.BiFunction;
import java.util.function.Function;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@ToString
@EqualsAndHashCode
public class MotorcycleEditModel {
    private String engineType;
    private String color;
    private float weight;
    private String productionDate;

    public static Function<Motorcycle, MotorcycleEditModel> entityToModelMapper(
            Function<EngineType, String> engineTypeStringFunction
    ){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        return motorcycle -> MotorcycleEditModel.builder()
                .engineType(engineTypeStringFunction.apply(motorcycle.getEngineType()))
                .color(motorcycle.getColor())
                .weight(motorcycle.getWeight())
                .productionDate(motorcycle.getProductionDate().format(formatter))
                .build();
    }

    public static BiFunction<Motorcycle, MotorcycleEditModel, Motorcycle> modelToEntityMapper(
            Function<String, EngineType> engineTypeFunction
    ){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        return (motorcycle, motorcycleEditModel) -> {
            motorcycle.setEngineType(engineTypeFunction.apply(motorcycleEditModel.getEngineType()));
            motorcycle.setColor(motorcycleEditModel.getColor());
            motorcycle.setWeight(motorcycleEditModel.getWeight());
            motorcycle.setProductionDate(LocalDate.parse(motorcycleEditModel.productionDate, formatter));
            return motorcycle;
        };
    }
}
