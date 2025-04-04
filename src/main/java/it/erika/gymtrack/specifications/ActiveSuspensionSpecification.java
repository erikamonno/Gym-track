package it.erika.gymtrack.specifications;

import it.erika.gymtrack.entities.Subscription_;
import it.erika.gymtrack.entities.Suspension;
import it.erika.gymtrack.entities.Suspension_;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import java.time.Instant;
import java.util.UUID;
import lombok.Data;
import org.springframework.data.jpa.domain.Specification;

@Data
public class ActiveSuspensionSpecification implements Specification<Suspension> {

    private final UUID subscriptionId;
    private final Instant atDate;

    @Override
    public Predicate toPredicate(Root<Suspension> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
        return subscriptionIdEqual()
                .and(endDateGreaterThenOrEqualTo().or(endDateIsNull()))
                .toPredicate(root, query, criteriaBuilder);
    }

    private Specification<Suspension> subscriptionIdEqual() {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.equal(root.get(Suspension_.subscription).get(Subscription_.id), subscriptionId);
    }

    private Specification<Suspension> endDateGreaterThenOrEqualTo() {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.greaterThanOrEqualTo(root.get(Suspension_.endDate), atDate);
    }

    private Specification<Suspension> endDateIsNull() {
        return (root, query, criteriaBuilder) -> criteriaBuilder.isNull(root.get(Suspension_.endDate));
    }
}
