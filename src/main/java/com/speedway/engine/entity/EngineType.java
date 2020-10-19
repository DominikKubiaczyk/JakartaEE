package com.speedway.engine.entity;

import lombok.*;

import java.io.Serializable;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@EqualsAndHashCode
@Builder
public class EngineType implements Serializable {
    private UUID id;
    private int size;
    private EngineProducers producer;
    private String tuner;
    private float maxSpeed;
}