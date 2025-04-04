package it.erika.gymtrack.specifications;

import it.erika.gymtrack.entities.Subscription;
import it.erika.gymtrack.entities.Subscription_;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import java.time.Duration;
import java.time.Instant;
import lombok.Data;
import org.springframework.data.jpa.domain.Specification;

@Data
public class ExpiringSoonSubscriptionSpecification implements Specification<Subscription> {

    private final Duration duration;

    @Override
    public Predicate toPredicate(Root<Subscription> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
        return getExpiringSoonSubscription().toPredicate(root, query, criteriaBuilder);
    }

    public Specification<Subscription> getExpiringSoonSubscription() {
        var expire = Instant.now().plus(duration);
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.lessThanOrEqualTo(root.get(Subscription_.endDate), expire);
    }
}
