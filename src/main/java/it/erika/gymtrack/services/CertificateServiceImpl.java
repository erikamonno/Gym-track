package it.erika.gymtrack.services;

import it.erika.gymtrack.dto.CertificateDto;
import it.erika.gymtrack.entities.Certificate;
import it.erika.gymtrack.exceptions.CertificateNotFoundException;
import it.erika.gymtrack.mappers.CertificateMapper;
import it.erika.gymtrack.repository.CertificateRepository;
import java.io.IOException;
import java.time.Instant;
import java.util.Optional;
import java.util.UUID;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class CertificateServiceImpl implements CertificateService {

    private final CertificateMapper mapper;
    private final CertificateRepository repository;

    public CertificateServiceImpl(CertificateMapper mapper, CertificateRepository repository) {
        this.mapper = mapper;
        this.repository = repository;
    }

    @Override
    public CertificateDto upload(UUID id, MultipartFile file, Instant expiryDate) throws IOException {
        Certificate entity = new Certificate();
        entity.setId(id);
        entity.setExpiryDate(expiryDate);
        entity.setContent(file.getBytes());
        entity = repository.save(entity);
        return mapper.toDto(entity);
    }

    @Override
    public CertificateDto getById(UUID id) {
        Optional<Certificate> oEntity = repository.findById(id);
        if (oEntity.isEmpty()) {
            throw new CertificateNotFoundException(HttpStatus.NOT_FOUND, "Certificate not found");
        }
        var entity = oEntity.get();
        return mapper.toDto(entity);
    }

    @Override
    public boolean existValidCertificate(UUID id) {
        Optional<Certificate> oEntity = repository.findById(id);
        if (oEntity.isEmpty()) {
            throw new CertificateNotFoundException(HttpStatus.NOT_FOUND, "Certificate not found");
        }
        var entity = oEntity.get();
        return entity.getExpiryDate().isAfter(Instant.now());
    }

    @Override
    public void delete(UUID id) {
        repository.deleteById(id);
    }
}
