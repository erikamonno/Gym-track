package it.erika.gymtrack.repository;

import it.erika.gymtrack.entities.Course;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.UUID;

public interface CourseRepository extends JpaRepository<Course, UUID>, JpaSpecificationExecutor<Course> {
}
