package it.erika.gymtrack.dto;

import it.erika.gymtrack.enumes.Status;
import it.erika.gymtrack.enumes.Type;
import jakarta.validation.constraints.NotNull;
import java.time.Instant;
import java.util.UUID;
import lombok.Data;

@Data
public class PaymentDto {

    private UUID id;

    private Instant paymentTimestamp;

    private Type type;

    private Status status;

    private String currency;

    private Double amount;

    @NotNull private SubscriptionDto subscription;
}
