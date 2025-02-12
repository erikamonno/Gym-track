package it.erika.gymtrack.filters;

import it.erika.gymtrack.enumes.SubscriptionType;
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

    private SubscriptionType type;

    private UUID customerId;
}
