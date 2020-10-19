package com.speedway.rider.dto;

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
public class GetRidersResponse {

    @Getter
    @Setter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor(access = AccessLevel.PRIVATE)
    @ToString
    @EqualsAndHashCode
    public static class Rider {
        private UUID id;
        private String firstName;
        private String lastName;
    }

    @Singular
    private List<Rider> riders;

    public static Function<Collection<com.speedway.rider.entity.Rider>, GetRidersResponse> entityToDtoMapper() {
        return riders -> {
            GetRidersResponse.GetRidersResponseBuilder resp = GetRidersResponse.builder();
            riders.stream()
                    .map(rider -> Rider.builder()
                            .id(rider.getId())
                            .firstName(rider.getFirstName())
                            .lastName(rider.getLastName())
                            .build())
                    .forEach(resp::rider);
            return resp.build();
        };
    }
}
