package it.erika.gymtrack.services;

import it.erika.gymtrack.dto.CertificateDto;
import it.erika.gymtrack.entities.Certificate;
import org.springframework.web.multipart.MultipartFile;

import java.util.UUID;

public interface CertificateService {

    CertificateDto upload(MultipartFile file, UUID customerId);
    CertificateDto getById(UUID id);
    boolean existValidCertificate(UUID customerId);
    void delete(UUID id);

}
