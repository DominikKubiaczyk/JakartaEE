package com.speedway.rider.dto;

import com.speedway.rider.entity.Rider;
import com.speedway.rider.entity.Role;
import lombok.*;

import java.time.LocalDate;
import java.util.UUID;
import java.util.function.Function;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@ToString
@EqualsAndHashCode
public class GetRiderResponse {
    private UUID id;
    private String firstName;
    private String lastName;
    private Role role;
    private int startNumber;
    private LocalDate birthDate;

    public static Function<Rider, GetRiderResponse> entityToDtoMapper() {
        return rider -> GetRiderResponse.builder()
                .firstName(rider.getFirstName())
                .lastName(rider.getLastName())
                .role(rider.getRole())
                .startNumber(rider.getStartNumber())
                .birthDate(rider.getBirthDate())
                .build();
    }
}
