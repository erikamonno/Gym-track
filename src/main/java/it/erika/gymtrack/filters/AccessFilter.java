package it.erika.gymtrack.filters;

import java.time.Instant;
import java.util.UUID;
import lombok.Data;

@Data
public class AccessFilter {

    private Instant accessDateFrom;

    private Instant accessDateTo;

    private UUID customerId;
}
