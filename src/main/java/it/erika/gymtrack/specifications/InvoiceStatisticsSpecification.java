package it.erika.gymtrack.specifications;

import it.erika.gymtrack.entities.Payment;
import it.erika.gymtrack.entities.Payment_;
import it.erika.gymtrack.enumes.Status;
import it.erika.gymtrack.filters.InvoiceStatisticsFilter;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import org.springframework.data.jpa.domain.Specification;

public class InvoiceStatisticsSpecification implements Specification<Payment> {

    private final InvoiceStatisticsFilter filter;

    public InvoiceStatisticsSpecification(InvoiceStatisticsFilter filter) {
        this.filter = filter;
    }

    @Override
    public Predicate toPredicate(Root<Payment> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
        return Specification.allOf(dateFromGraterThenOrEqualTo(), dateToLessThanOrEqualTo(), statusIsDone())
                .toPredicate(root, query, criteriaBuilder);
    }

    public Specification<Payment> dateFromGraterThenOrEqualTo() {
        return (root, query, criteriaBuilder) -> {
            if (filter.getDateFrom() == null) {
                return null;
            } else {
                return criteriaBuilder.greaterThanOrEqualTo(root.get(Payment_.paymentTimestamp), filter.getDateFrom());
            }
        };
    }

    public Specification<Payment> dateToLessThanOrEqualTo() {
        return (root, query, criteriaBuilder) -> {
            if (filter.getDateTo() == null) {
                return null;
            } else {
                return criteriaBuilder.lessThanOrEqualTo(root.get(Payment_.paymentTimestamp), filter.getDateTo());
            }
        };
    }

    public Specification<Payment> statusIsDone() {
        return (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get(Payment_.status), Status.DONE);
    }
}
