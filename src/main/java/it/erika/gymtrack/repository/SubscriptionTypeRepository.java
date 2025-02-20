package it.erika.gymtrack.repository;

import it.erika.gymtrack.entities.SubscriptionType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.UUID;

public interface SubscriptionTypeRepository extends JpaRepository<SubscriptionType, UUID>, JpaSpecificationExecutor<SubscriptionType> {
}
