package it.erika.gymtrack.filters;

import java.time.Instant;
import lombok.Data;

@Data
public class SubscriptionStatisticsFilter {

    private Instant startDate;
    private Instant endDate;
}
