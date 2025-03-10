package it.erika.gymtrack.repository;

import it.erika.gymtrack.entities.Suspension;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.UUID;

public interface SuspensionRepository extends JpaRepository<Suspension, UUID>, JpaSpecificationExecutor<Suspension> {
}
