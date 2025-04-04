package it.erika.gymtrack.repository;

import it.erika.gymtrack.entities.Access;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface AccessRepository extends JpaRepository<Access, UUID>, JpaSpecificationExecutor<Access> {}
