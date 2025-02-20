package it.erika.gymtrack.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.UUID;

@Data
public class SubscriptionTypeDto {

    private UUID id;

    @NotBlank
    private String name;

    private String description;

    @NotNull
    private Integer durationInDays;

    private Integer maxDailyAccesses;
}
