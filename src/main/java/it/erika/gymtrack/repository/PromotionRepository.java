package it.erika.gymtrack.repository;

import it.erika.gymtrack.dto.PromotionDto;
import it.erika.gymtrack.entities.Promotion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;
import java.util.UUID;

public interface PromotionRepository extends JpaRepository<Promotion, UUID>, JpaSpecificationExecutor<Promotion> {

    List<Promotion> findBySubscriptionTypeId(UUID subscriptionTypeId);
}
