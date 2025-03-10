package it.erika.gymtrack.specifications;

import it.erika.gymtrack.entities.Subscription_;
import it.erika.gymtrack.entities.Suspension;
import it.erika.gymtrack.entities.Suspension_;
import it.erika.gymtrack.filters.SuspensionFilter;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import lombok.Data;
import org.springframework.data.jpa.domain.Specification;

@Data
public class SuspensionSpecification implements Specification<Suspension> {

    private final SuspensionFilter filter;

    @Override
    public Predicate toPredicate(Root<Suspension> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
        return Specification.allOf(
                startDateFrom(),
                startDateTo(),
                endDateFrom(),
                endDateTo(),
                reasonEqual(),
                subscriptionIdEqual()
        ).toPredicate(root, query, criteriaBuilder);
    }

    private Specification<Suspension> startDateFrom() {
        return (root, query, criteriaBuilder) -> {
            if(filter.getStartDateFrom()==null) {
                return null;
            } else {
                return criteriaBuilder.greaterThanOrEqualTo(root.get(Suspension_.startDate), filter.getStartDateFrom());
            }
        };
    }

    private Specification<Suspension> startDateTo() {
        return (root, query, criteriaBuilder) -> {
            if(filter.getStartDateTo()==null) {
                return null;
            } else {
                return criteriaBuilder.lessThanOrEqualTo(root.get(Suspension_.startDate), filter.getStartDateTo());
            }
        };
    }

    private Specification<Suspension> endDateFrom() {
        return (root, query, criteriaBuilder) -> {
            if(filter.getEndDateFrom()==null) {
                return null;
            } else {
                return criteriaBuilder.greaterThanOrEqualTo(root.get(Suspension_.endDate), filter.getEndDateFrom());
            }
        };
    }

    private Specification<Suspension> endDateTo() {
        return (root, query, criteriaBuilder) -> {
            if(filter.getEndDateTo()==null) {
                return null;
            } else {
                return criteriaBuilder.lessThanOrEqualTo(root.get(Suspension_.endDate), filter.getEndDateTo());
            }
        };
    }


    private Specification<Suspension> reasonEqual() {
        return (root, query, criteriaBuilder) -> {
            if(filter.getReason()==null) {
                return null;
            } else {
                return criteriaBuilder.equal(root.get(Suspension_.reason), filter.getReason());
            }
        };
    }

    private Specification<Suspension> subscriptionIdEqual() {
        return (root, query, criteriaBuilder) -> {
            if(filter.getSubscriptionId()==null) {
                return null;
            } else {
                return criteriaBuilder.equal(root.get(Suspension_.subscription).get(Subscription_.id), filter.getSubscriptionId());
            }
        };
    }
}
