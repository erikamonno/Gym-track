package it.erika.gymtrack.filters;

import lombok.Data;

import java.time.Instant;
import java.time.LocalDate;

@Data
public class SubscriptionStatisticsFilter {

    private Instant startDate;
    private Instant endDate;
}
