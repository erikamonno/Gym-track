package it.erika.gymtrack.filters;


import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import java.time.Instant;
import java.util.UUID;

@Data
public class SubscriptionFilter {

    private Instant startDateFrom;

    private Instant startDateTo;

    private Instant endDateFrom;

    private Instant endDateTo;

    private UUID subscriptionTypeId;

    private UUID customerId;
}
