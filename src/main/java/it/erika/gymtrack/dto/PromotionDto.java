package it.erika.gymtrack.dto;

import jakarta.validation.constraints.*;
import lombok.Data;

import java.time.Instant;
import java.util.UUID;

@Data
public class PromotionDto {

    private UUID id;

    @NotBlank
    private String name;

    @NotNull
    @PositiveOrZero
    private Double amount;

    @NotNull
    @FutureOrPresent
    private Instant validFrom;

    @NotNull
    @Future
    private Instant validTo;

}

