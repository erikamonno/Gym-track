package it.erika.gymtrack.filters;

import java.time.Instant;
import lombok.Data;

@Data
public class InvoiceStatisticsFilter {

    private Instant dateFrom;

    private Instant dateTo;
}
