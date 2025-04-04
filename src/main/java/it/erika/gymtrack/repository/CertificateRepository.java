package it.erika.gymtrack.repository;

import it.erika.gymtrack.entities.Certificate;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface CertificateRepository
        extends JpaRepository<Certificate, UUID>, JpaSpecificationExecutor<Certificate> {}
