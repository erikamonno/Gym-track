package it.erika.gymtrack.repository;

import it.erika.gymtrack.entities.Access;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.UUID;

public interface AccessRepository extends JpaRepository<Access, UUID>, JpaSpecificationExecutor<Access> {
}
