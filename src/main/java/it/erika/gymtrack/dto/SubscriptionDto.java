package it.erika.gymtrack.dto;

import it.erika.gymtrack.enumes.SubscriptionType;
import jakarta.persistence.Column;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import java.time.Instant;
import java.util.UUID;

@Data
public class SubscriptionDto {
    private UUID id;

    @NotBlank
    private Instant startDate;

    @NotBlank
    private Instant endDate;

    private SubscriptionType type;
}
