package it.erika.gymtrack.dto;

import it.erika.gymtrack.enumes.Reason;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.Instant;
import java.util.UUID;

@Data
public class SuspensionDto {

    private UUID id;

    @NotNull
    private Instant startDate;

    private Instant endDate;

    @NotNull
    private Reason reason;

    private String note;

    @NotNull
    private SubscriptionDto subscription;

    @NotNull
    private Boolean refundSuspension;
}
