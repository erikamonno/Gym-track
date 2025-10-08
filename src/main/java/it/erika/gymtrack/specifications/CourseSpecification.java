package it.erika.gymtrack.specifications;

import it.erika.gymtrack.entities.Course;
import it.erika.gymtrack.entities.Course_;
import it.erika.gymtrack.filters.CourseFilter;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import java.time.Instant;
import lombok.Data;
import org.springframework.data.jpa.domain.Specification;

@Data
public class CourseSpecification implements Specification<Course> {

    private final CourseFilter filter;

    @Override
    public Predicate toPredicate(Root<Course> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
        return Specification.allOf(nameEqual(), active()).toPredicate(root, query, criteriaBuilder);
    }

    public Specification<Course> nameEqual() {
        return (root, query, criteriaBuilder) -> {
            if (filter.getName() == null) {
                return null;
            } else {
                return criteriaBuilder.equal(root.get(Course_.name), filter.getName());
            }
        };
    }

    public Specification<Course> active() {
        return (root, query, criteriaBuilder) -> {
            if (filter.getActive() == null) {
                return null;
            } else if (filter.getActive()) {
                return criteriaBuilder.between(
                        criteriaBuilder.currentTimestamp().as(Instant.class),
                        root.get(Course_.validFrom),
                        root.get(Course_.validTo));
            } else {
                return criteriaBuilder.not(criteriaBuilder.between(
                        criteriaBuilder.currentTimestamp().as(Instant.class),
                        root.get(Course_.validFrom),
                        root.get(Course_.validTo)));
            }
        };
    }
}
