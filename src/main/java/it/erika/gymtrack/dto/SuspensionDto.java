package it.erika.gymtrack.dto;

import it.erika.gymtrack.enumes.Reason;
import jakarta.validation.constraints.NotNull;
import java.time.Instant;
import java.util.UUID;
import lombok.Data;

@Data
public class SuspensionDto {

    private UUID id;

    @NotNull private Instant startDate;

    private Instant endDate;

    @NotNull private Reason reason;

    private String note;

    @NotNull private SubscriptionDto subscription;

    @NotNull private Boolean refundSuspension;

    private Instant originalSubscriptionEndDate;
}
