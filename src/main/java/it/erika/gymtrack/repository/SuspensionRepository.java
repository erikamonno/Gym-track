package it.erika.gymtrack.repository;

import it.erika.gymtrack.entities.Suspension;
import it.erika.gymtrack.entities.Suspension_;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface SuspensionRepository extends JpaRepository<Suspension, UUID>, JpaSpecificationExecutor<Suspension> {

    @Override
    @EntityGraph(attributePaths = Suspension_.SUBSCRIPTION)
    Optional<Suspension> findById(UUID id);
}
