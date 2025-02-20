package it.erika.gymtrack.dto;

import it.erika.gymtrack.entities.SubscriptionType;
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

    @NotNull
    private SubscriptionTypeDto subscriptionType;

    @NotNull
    private CustomerDto customer;
}
