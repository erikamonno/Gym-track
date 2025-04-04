package it.erika.gymtrack.repository;

import it.erika.gymtrack.entities.SubscriptionType;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface SubscriptionTypeRepository
        extends JpaRepository<SubscriptionType, UUID>, JpaSpecificationExecutor<SubscriptionType> {}
