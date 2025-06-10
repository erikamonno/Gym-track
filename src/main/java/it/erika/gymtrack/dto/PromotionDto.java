package it.erika.gymtrack.dto;

import jakarta.validation.constraints.*;
import java.time.Instant;
import java.util.UUID;
import lombok.Data;

@Data
public class PromotionDto {

    private UUID id;

    @NotBlank
    private String name;

    @NotNull @PositiveOrZero
    private Double amount;

    @NotNull @FutureOrPresent
    private Instant validFrom;

    @NotNull @Future
    private Instant validTo;
}
