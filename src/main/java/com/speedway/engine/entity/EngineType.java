package com.speedway.engine.entity;

import com.speedway.motorcycle.entity.Motorcycle;
import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor
@ToString
@EqualsAndHashCode
@Builder
@Entity
@Table(name = "engineTypes")
public class EngineType implements Serializable {

    @Id
    @Builder.Default
    private UUID id = UUID.randomUUID();

    private int size;

    private EngineProducers producer;

    private String tuner;

    private float maxSpeed;

    @OneToMany(mappedBy = "engineType", cascade = CascadeType.REMOVE)
    private List<Motorcycle> motorcycles;
}