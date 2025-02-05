package it.erika.gymtrack.services;

import it.erika.gymtrack.dto.CertificateDto;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.UUID;

@Service
public class CertificateServiceImpl implements CertificateService {
    @Override
    public CertificateDto upload(MultipartFile file, UUID customerId) {
        return null;
    }

    @Override
    public CertificateDto getById(UUID id) {
        return null;
    }

    @Override
    public boolean existValidCertificate(UUID customerId) {
        return false;
    }

    @Override
    public void delete(UUID id) {

    }
}
