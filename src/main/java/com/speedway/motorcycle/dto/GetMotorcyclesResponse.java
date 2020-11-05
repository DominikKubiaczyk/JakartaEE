package com.speedway.motorcycle.dto;

import lombok.*;

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
public class GetMotorcyclesResponse {

    @Getter
    @Setter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor(access = AccessLevel.PRIVATE)
    @ToString
    @EqualsAndHashCode
    public static class Motorcycle {
        private UUID id;
        private String color;
    }

    @Singular
    private List<Motorcycle> motorcycles;

    public static Function<Collection<com.speedway.motorcycle.entity.Motorcycle>, GetMotorcyclesResponse> entityToDtoMapper() {
        return motorcycles -> {
            GetMotorcyclesResponseBuilder response = GetMotorcyclesResponse.builder();
                    motorcycles.stream()
                            .map(motorcycle -> Motorcycle.builder()
                                        .id(motorcycle.getId())
                                        .color(motorcycle.getColor())
                                        .build())
                            .forEach(response::motorcycle);
            return response.build();
        };
    }
}
