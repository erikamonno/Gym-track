package it.erika.gymtrack.specifications;

import it.erika.gymtrack.entities.Subscription;
import it.erika.gymtrack.entities.Subscription_;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import java.time.Instant;
import lombok.Data;
import org.springframework.data.jpa.domain.Specification;

@Data
public class ExpiredSubscriptionSpecification implements Specification<Subscription> {

    @Override
    public Predicate toPredicate(Root<Subscription> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
        return getExpiredSubscription().toPredicate(root, query, criteriaBuilder);
    }

    public Specification<Subscription> getExpiredSubscription() {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.lessThan(root.get(Subscription_.endDate), Instant.now());
    }
}
