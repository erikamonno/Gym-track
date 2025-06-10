package it.erika.gymtrack.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import java.util.UUID;
import lombok.Data;

@Data
public class SubscriptionTypeDto {

    private UUID id;

    @NotBlank
    private String name;

    private String description;

    @NotNull @Positive private Integer durationInDays;

    @Positive private Integer maxDailyAccesses;

    @NotBlank
    private String currency;

    @NotNull @PositiveOrZero // posso fare anche la prova gratuita di un giorno
    private Double amount;
}
