package it.erika.gymtrack.filters;

import it.erika.gymtrack.enumes.SubscriptionType;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import java.time.Instant;

@Data
public class SubscriptionFilter {

    private Instant startDateFrom;

    private Instant startDateTo;

    private Instant endDateFrom;

    private Instant endDateTo;

    private SubscriptionType type;
}
