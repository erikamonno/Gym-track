package it.erika.gymtrack.repository;

import it.erika.gymtrack.entities.CourseSchedule;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface CourseScheduleRepository
        extends JpaRepository<CourseSchedule, UUID>, JpaSpecificationExecutor<CourseSchedule> {}
