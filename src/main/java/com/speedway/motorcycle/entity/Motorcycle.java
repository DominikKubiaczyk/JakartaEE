package com.speedway.motorcycle.entity;

import com.speedway.engine.entity.EngineType;
import com.speedway.rider.entity.Rider;
import lombok.*;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@EqualsAndHashCode
@Builder
public class Motorcycle implements Serializable {
    private UUID id;
    private EngineType engineType;
    private String color;
    private float weight;
    private LocalDate productionDate;
}