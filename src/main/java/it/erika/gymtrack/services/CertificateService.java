package it.erika.gymtrack.services;

import it.erika.gymtrack.dto.CertificateDto;
import it.erika.gymtrack.entities.Certificate;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.Instant;
import java.util.UUID;

public interface CertificateService {

    CertificateDto upload(UUID id, MultipartFile file, Instant expiryDate) throws IOException;
    CertificateDto getById(UUID id);
    boolean existValidCertificate(UUID id);
    void delete(UUID id);

}
