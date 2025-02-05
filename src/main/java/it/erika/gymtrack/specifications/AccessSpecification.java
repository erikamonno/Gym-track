package it.erika.gymtrack.specifications;

import it.erika.gymtrack.entities.Access;
import it.erika.gymtrack.filters.AccessFilter;
import it.erika.gymtrack.filters.CustomerFilter;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import lombok.Data;
import org.springframework.data.jpa.domain.Specification;

@Data
public class AccessSpecification implements Specification<Access> {

    private final AccessFilter filter;

    @Override
    public Predicate toPredicate(Root<Access> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
        return Specification.allOf(
                accessDateFrom(),
                accessDateTo(),
                customerIdEqual()
        ).toPredicate(root, query, criteriaBuilder);
    }

    public Specification<Access> accessDateFrom() {
        return (root, query, criteriaBuilder) -> {
            if(filter.getAccessDateFrom()==null) {
                return null;
            } else {
                return criteriaBuilder.greaterThanOrEqualTo(root.get("accessDate"), filter.getAccessDateFrom());
            }
        };
    }

    public Specification<Access> accessDateTo() {
        return (root, query, criteriaBuilder) -> {
            if(filter.getAccessDateTo()==null) {
                return null;
            } else {
                return criteriaBuilder.lessThanOrEqualTo(root.get("accessDate"), filter.getAccessDateTo());
            }
        };
    }

    public Specification<Access> customerIdEqual() {
        return (root, query, criteriaBuilder) -> {
            if(filter.getCustomerId()==null) {
                return null;
            } else {
                return criteriaBuilder.equal(root.get("customer"), filter.getCustomerId());
            }
        };
    }
}
