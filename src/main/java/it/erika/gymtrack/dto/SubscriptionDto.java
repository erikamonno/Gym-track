package it.erika.gymtrack.dto;

import it.erika.gymtrack.enumes.SubscriptionType;
import jakarta.persistence.Column;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.Instant;
import java.util.UUID;

@Data
public class SubscriptionDto {

    private UUID id;

    @NotNull
    private Instant startDate;

    @NotNull
    private Instant endDate;

    private SubscriptionType type;

    @NotNull
    private CustomerDto customer;
}
