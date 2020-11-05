package com.speedway.motorcycle.dto;

import com.speedway.engine.entity.EngineType;
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
public class CreateMotorcycleRequest {
    private String color;
    private float weight;
    private String productionDate;

    public static Function<CreateMotorcycleRequest, Motorcycle> dtoToEntityMapper(
            EngineType engineType
    ){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        return request -> Motorcycle.builder()
                .productionDate(LocalDate.parse(request.getProductionDate(), formatter))
                .id(UUID.randomUUID())
                .weight(request.getWeight())
                .color(request.getColor())
                .engineType(engineType)
                .build();
    }
}
