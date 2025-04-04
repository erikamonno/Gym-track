package it.erika.gymtrack.dto;

import jakarta.validation.constraints.NotNull;
import java.time.Instant;
import java.util.UUID;
import lombok.Data;

@Data
public class CertificateDto {
    @NotNull private UUID id;

    @NotNull private Instant expiryDate;

    @NotNull private byte[] content;
}
