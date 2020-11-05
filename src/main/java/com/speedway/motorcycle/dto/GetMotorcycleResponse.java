package com.speedway.motorcycle.dto;

import com.speedway.motorcycle.entity.Motorcycle;
import lombok.*;

import java.time.format.DateTimeFormatter;
import java.util.function.Function;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@ToString
@EqualsAndHashCode
public class GetMotorcycleResponse {
    private String engineType;
    private String color;
    private float weight;
    private String productionDate;

    public static Function<Motorcycle, GetMotorcycleResponse> entityToDtoMapper(){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        return motorcycle -> GetMotorcycleResponse.builder()
                .color(motorcycle.getColor())
                .engineType(motorcycle.getEngineType().getProducer() + " " + motorcycle.getEngineType().getSize())
                .weight(motorcycle.getWeight())
                .productionDate(motorcycle.getProductionDate().format(formatter))
                .build();
    }
}
