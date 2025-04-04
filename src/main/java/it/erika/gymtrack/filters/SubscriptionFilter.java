package it.erika.gymtrack.filters;

import java.time.Instant;
import java.util.UUID;
import lombok.Data;

@Data
public class SubscriptionFilter {

    private Instant startDateFrom;

    private Instant startDateTo;

    private Instant endDateFrom;

    private Instant endDateTo;

    private UUID subscriptionTypeId;

    private UUID customerId;
}
