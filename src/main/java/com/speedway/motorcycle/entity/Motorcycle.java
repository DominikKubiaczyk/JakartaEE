package com.speedway.motorcycle.entity;

import com.speedway.engine.entity.EngineType;

import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor
@ToString
@EqualsAndHashCode
@Builder
@Entity
@Table(name = "motorcycles")
public class Motorcycle implements Serializable {

    @Id
    @Builder.Default
    private UUID id = UUID.randomUUID();

    private String color;

    private float weight;

    private LocalDate productionDate;

    @ManyToOne
    @JoinColumn(name = "engineType")
    private EngineType engineType;
}