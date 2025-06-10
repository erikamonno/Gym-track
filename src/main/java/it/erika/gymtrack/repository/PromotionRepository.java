package it.erika.gymtrack.repository;

import it.erika.gymtrack.entities.Promotion;
import java.util.List;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface PromotionRepository extends JpaRepository<Promotion, UUID>, JpaSpecificationExecutor<Promotion> {

    List<Promotion> findBySubscriptionTypeId(UUID subscriptionTypeId);
}
