package it.erika.gymtrack.repository;

import it.erika.gymtrack.entities.CourseSchedule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.UUID;

public interface CourseScheduleRepository extends JpaRepository<CourseSchedule, UUID>, JpaSpecificationExecutor<CourseSchedule> {
}
