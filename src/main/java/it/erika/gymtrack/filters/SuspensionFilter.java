package it.erika.gymtrack.filters;

import it.erika.gymtrack.enumes.Reason;
import lombok.Data;

import java.time.Instant;
import java.util.UUID;

@Data
public class SuspensionFilter {

    private Instant startDateFrom;

    private Instant startDateTo;

    private Instant endDateFrom;

    private Instant endDateTo;

    private Reason reason;

    private UUID subscriptionId;
}
