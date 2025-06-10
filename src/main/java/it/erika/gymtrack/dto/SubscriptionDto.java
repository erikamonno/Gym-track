package it.erika.gymtrack.dto;

import jakarta.validation.constraints.NotNull;
import java.time.Instant;
import java.util.UUID;
import lombok.Data;

@Data
public class SubscriptionDto {

    private UUID id;

    @NotNull private Instant startDate;

    private Instant endDate;

    @NotNull private SubscriptionTypeDto subscriptionType;

    @NotNull private CustomerDto customer;

    private PromotionDto promotion;
}
