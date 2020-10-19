package com.speedway.rider.entity;

import lombok.*;

import java.io.Serializable;
import java.nio.file.Path;
import java.time.LocalDate;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@EqualsAndHashCode
@Builder
public class Rider implements Serializable {
    private UUID id;
    private String firstName;
    private String lastName;
    private Role role;
    private int startNumber;
    private LocalDate birthDate;
    private String avatarPath;
}