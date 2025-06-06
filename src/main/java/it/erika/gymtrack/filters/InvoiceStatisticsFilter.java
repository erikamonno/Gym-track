package it.erika.gymtrack.filters;

import lombok.Data;

import java.time.Instant;

@Data
public class InvoiceStatisticsFilter {

    private Instant dateFrom;

    private Instant dateTo;

}
