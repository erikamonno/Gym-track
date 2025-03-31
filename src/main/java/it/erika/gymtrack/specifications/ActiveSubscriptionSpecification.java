package it.erika.gymtrack.specifications;

import it.erika.gymtrack.entities.Subscription;
import it.erika.gymtrack.entities.Subscription_;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import lombok.Data;
import org.springframework.data.jpa.domain.Specification;

import java.time.Instant;

@Data
public class ActiveSubscriptionSpecification implements Specification<Subscription> {


    @Override
    public Predicate toPredicate(Root<Subscription> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
        return getActiveSubscription().toPredicate(root, query, criteriaBuilder);
    }

    public Specification<Subscription> getActiveSubscription() {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.greaterThan(root.get(Subscription_.endDate), Instant.now());

    }
}

