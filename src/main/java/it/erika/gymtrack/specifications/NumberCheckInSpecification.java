package it.erika.gymtrack.specifications;

import it.erika.gymtrack.entities.Access;
import it.erika.gymtrack.entities.Access_;
import it.erika.gymtrack.filters.SubscriptionStatisticsFilter;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import lombok.Data;
import org.springframework.data.jpa.domain.Specification;
@Data

public class NumberCheckInSpecification implements Specification<Access> {

    private final SubscriptionStatisticsFilter filter;

    @Override
    public Predicate toPredicate(Root<Access> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
        return getNumberCheckInBetween().toPredicate(root, query, criteriaBuilder);
    }

    private Specification<Access> getNumberCheckInBetween() {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.between(root.get(Access_.accessDate), filter.getStartDate(), filter.getEndDate());

    }
}
