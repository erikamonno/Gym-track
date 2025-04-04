package it.erika.gymtrack.services;

import it.erika.gymtrack.dto.CertificateDto;
import java.io.IOException;
import java.time.Instant;
import java.util.UUID;
import org.springframework.web.multipart.MultipartFile;

public interface CertificateService {

    CertificateDto upload(UUID id, MultipartFile file, Instant expiryDate) throws IOException;

    CertificateDto getById(UUID id);

    boolean existValidCertificate(UUID id);

    void delete(UUID id);
}
