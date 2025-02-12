package it.erika.gymtrack.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.Instant;
import java.util.UUID;

@Data
public class CertificateDto {
    @NotNull
    private UUID id;

    @NotNull
    private Instant expiryDate;

    @NotNull
    private byte[] content;
}
