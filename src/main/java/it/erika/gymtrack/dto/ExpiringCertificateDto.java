package it.erika.gymtrack.dto;

import java.time.LocalDate;
import java.util.UUID;
import lombok.Data;

@Data
public class ExpiringCertificateDto {

    private UUID clientId;

    private String name;

    private LocalDate expiresOn;
}
