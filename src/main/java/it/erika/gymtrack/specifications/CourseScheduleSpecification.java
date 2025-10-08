package it.erika.gymtrack.specifications;

import it.erika.gymtrack.entities.CourseSchedule;
import it.erika.gymtrack.entities.CourseSchedule_;
import it.erika.gymtrack.entities.Course_;
import it.erika.gymtrack.filters.CourseScheduleFilter;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import org.springframework.data.jpa.domain.Specification;

public class CourseScheduleSpecification implements Specification<CourseSchedule> {

    private final CourseScheduleFilter filter;

    public CourseScheduleSpecification(CourseScheduleFilter filter) {
        this.filter = filter;
    }

    @Override
    public Predicate toPredicate(Root<CourseSchedule> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
        return Specification.allOf(dayEqual(), courseIdEqual()).toPredicate(root, query, criteriaBuilder);
    }

    public Specification<CourseSchedule> dayEqual() {
        return (root, query, criteriaBuilder) -> {
            if (filter.getDay() == null) {
                return null;
            } else {
                return criteriaBuilder.equal(root.get(CourseSchedule_.day), filter.getDay());
            }
        };
    }

    public Specification<CourseSchedule> courseIdEqual() {
        return (root, query, criteriaBuilder) -> {
            if (filter.getCourseId() == null) {
                return null;
            } else {
                return criteriaBuilder.equal(root.get(CourseSchedule_.course).get(Course_.id), filter.getCourseId());
            }
        };
    }
}
