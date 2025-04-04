package it.erika.gymtrack.specifications;

import it.erika.gymtrack.entities.SubscriptionType;
import it.erika.gymtrack.entities.SubscriptionType_;
import it.erika.gymtrack.filters.SubscriptionTypeFilter;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import lombok.Data;
import org.springframework.data.jpa.domain.Specification;

@Data
public class SubscriptionTypeSpecification implements Specification<SubscriptionType> {

    private final SubscriptionTypeFilter filter;

    @Override
    public Predicate toPredicate(Root<SubscriptionType> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
        return Specification.allOf(nameEqual(), durationInDaysFrom(), durationInDaysTo())
                .toPredicate(root, query, criteriaBuilder);
    }

    public Specification<SubscriptionType> nameEqual() {
        return (root, query, criteriaBuilder) -> {
            if (filter.getName() == null) {
                return null;
            } else {
                return criteriaBuilder.equal(root.get(SubscriptionType_.name), filter.getName());
            }
        };
    }

    public Specification<SubscriptionType> durationInDaysFrom() {
        return (root, query, criteriaBuilder) -> {
            if (filter.getDurationInDaysFrom() == null) {
                return null;
            } else {
                return criteriaBuilder.greaterThanOrEqualTo(
                        root.get(SubscriptionType_.durationInDays), filter.getDurationInDaysFrom());
            }
        };
    }

    public Specification<SubscriptionType> durationInDaysTo() {
        return (root, query, criteriaBuilder) -> {
            if (filter.getDurationInDaysTo() == null) {
                return null;
            } else {
                return criteriaBuilder.lessThanOrEqualTo(
                        root.get(SubscriptionType_.durationInDays), filter.getDurationInDaysTo());
            }
        };
    }
}
