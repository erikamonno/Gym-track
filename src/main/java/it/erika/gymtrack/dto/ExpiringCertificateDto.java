package it.erika.gymtrack.dto;

import lombok.Data;

import java.time.LocalDate;
import java.util.UUID;

@Data
public class ExpiringCertificateDto {

    private UUID clientId;

    private String name;

    private LocalDate expiresOn;
}
