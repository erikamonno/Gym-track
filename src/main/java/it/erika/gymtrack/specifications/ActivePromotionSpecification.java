package it.erika.gymtrack.specifications;

import it.erika.gymtrack.entities.Promotion;
import it.erika.gymtrack.entities.Promotion_;
import it.erika.gymtrack.entities.SubscriptionType_;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import java.time.Instant;
import java.util.UUID;
import org.springframework.data.jpa.domain.Specification;

public class ActivePromotionSpecification implements Specification<Promotion> {

    private final UUID subscriptionTypeId;

    public ActivePromotionSpecification(UUID subscriptionTypeId) {
        this.subscriptionTypeId = subscriptionTypeId;
    }

    @Override
    public Predicate toPredicate(Root<Promotion> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
        return Specification.allOf(subscriptionTypeIdEqual(), validRangeDateBetween())
                .toPredicate(root, query, criteriaBuilder);
    }

    public Specification<Promotion> subscriptionTypeIdEqual() {
        return (root, query, criteriaBuilder) -> criteriaBuilder.equal(
                root.get(Promotion_.subscriptionType).get(SubscriptionType_.id), subscriptionTypeId);
    }

    public Specification<Promotion> validRangeDateBetween() {
        return (root, query, criteriaBuilder) -> criteriaBuilder.between(
                criteriaBuilder.currentTimestamp().as(Instant.class),
                root.get(Promotion_.validFrom),
                root.get(Promotion_.validTo));
    }
}
