package it.erika.gymtrack.specifications;

import it.erika.gymtrack.dto.SuspensionDto;
import it.erika.gymtrack.entities.Subscription_;
import it.erika.gymtrack.entities.Suspension;
import it.erika.gymtrack.entities.Suspension_;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import lombok.Data;
import org.springframework.data.jpa.domain.Specification;
@Data
public class ActiveSuspensionSpecification implements Specification<Suspension> {

    private final SuspensionDto dto;

    @Override
    public Predicate toPredicate(Root<Suspension> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
        return subscriptionIdEqual()
                .and(
                        endDateGreaterThenOrEqualTo().or(endDateIsNull())
                ).toPredicate(root, query, criteriaBuilder);
    }

    private Specification<Suspension> subscriptionIdEqual() {
        return (root, query, criteriaBuilder) -> {
            return criteriaBuilder.equal(root.get(Suspension_.subscription).get(Subscription_.id), dto.getSubscription().getId());
        };
    }

    private Specification<Suspension> endDateGreaterThenOrEqualTo() {
        return (root, query, criteriaBuilder) -> {
            return criteriaBuilder.greaterThanOrEqualTo(root.get(Suspension_.endDate), dto.getEndDate());
        };
    }

    private Specification<Suspension> endDateIsNull() {
        return (root, query, criteriaBuilder) -> {
            return criteriaBuilder.isNull(root.get(Suspension_.endDate));
        };
    }

}
