package com.speedway.motorcycle.dto;

import com.speedway.motorcycle.entity.Motorcycle;
import lombok.*;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.function.BiFunction;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@ToString
@EqualsAndHashCode
public class UpdateMotorcycleRequest {
    private String color;
    private float weight;
    private String productionDate;

    public static BiFunction<Motorcycle, UpdateMotorcycleRequest, Motorcycle> dtoToEntityUpdater(){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        return (motorcycle, request) -> {
            motorcycle.setProductionDate(LocalDate.parse(request.getProductionDate(), formatter));
            motorcycle.setWeight(request.getWeight());
            motorcycle.setColor(request.getColor());
            return motorcycle;
        };
    }
}
