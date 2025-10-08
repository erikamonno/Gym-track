package it.erika.gymtrack.services;

import it.erika.gymtrack.dto.CourseScheduleDto;
import it.erika.gymtrack.filters.CourseScheduleFilter;
import java.util.UUID;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface CourseScheduleService {

    CourseScheduleDto insertCourseSchedule(CourseScheduleDto dto);

    CourseScheduleDto getCourseSchedule(UUID id);

    Page<CourseScheduleDto> searchCourseSchedule(Pageable pageable, CourseScheduleFilter filter);

    void updateCourseSchedule(UUID id, CourseScheduleDto dto);

    void deleteCourseSchedule(UUID id);
}
