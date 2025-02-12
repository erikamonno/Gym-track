package it.erika.gymtrack.specifications;

import it.erika.gymtrack.entities.Customer;
import it.erika.gymtrack.entities.Customer_;
import it.erika.gymtrack.entities.Subscription;
import it.erika.gymtrack.entities.Subscription_;
import it.erika.gymtrack.filters.SubscriptionFilter;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import lombok.Data;
import org.springframework.data.jpa.domain.Specification;
@Data
public class SubscriptionSpecification implements Specification<Subscription> {

    private final SubscriptionFilter filter;

    @Override
    public Predicate toPredicate(Root<Subscription> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
        return Specification.allOf(
                startDateFrom(),
                startDateTo(),
                endDateFrom(),
                endDateTo(),
                typeEqual(),
                customerIdEqual()
        ).toPredicate(root, query, criteriaBuilder);
    }

    public Specification<Subscription> startDateFrom() {
        return (root, query, criteriaBuilder) -> {
            if(filter.getStartDateFrom()==null) {
                return null;
            } else {
                return criteriaBuilder.greaterThanOrEqualTo(root.get(Subscription_.startDate), filter.getStartDateFrom());
            }
        };
    }

    public Specification<Subscription> startDateTo() {
        return (root, query, criteriaBuilder) -> {
            if(filter.getStartDateTo()==null) {
                return null;
            } else {
                return criteriaBuilder.lessThanOrEqualTo(root.get(Subscription_.startDate), filter.getStartDateTo());
            }
        };
    }

    public Specification<Subscription> endDateFrom() {
        return (root, query, criteriaBuilder) -> {
            if(filter.getEndDateFrom()==null) {
                return null;
            } else {
                return criteriaBuilder.greaterThanOrEqualTo(root.get(Subscription_.endDate), filter.getEndDateFrom());
            }
        };
    }

    public Specification<Subscription> endDateTo() {
        return (root, query, criteriaBuilder) -> {
            if(filter.getEndDateTo()==null) {
                return null;
            } else {
                return criteriaBuilder.lessThanOrEqualTo(root.get(Subscription_.endDate), filter.getEndDateTo());
            }
        };
    }

    public Specification<Subscription> typeEqual() {
        return (root, query, criteriaBuilder) -> {
            if(filter.getType()==null) {
                return null;
            } else {
                return criteriaBuilder.equal(root.get(Subscription_.type), filter.getType());
            }
        };
    }

    public Specification<Subscription> customerIdEqual() {
        return (root, query, criteriaBuilder) -> {
            if(filter.getCustomerId()==null) {
                return null;
            } else {
                return criteriaBuilder.equal(root.get(Subscription_.customer).get(Customer_.id), filter.getCustomerId());
            }
        };
    }
}
