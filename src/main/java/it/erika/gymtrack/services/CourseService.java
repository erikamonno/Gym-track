package it.erika.gymtrack.services;

import it.erika.gymtrack.dto.CourseDto;
import it.erika.gymtrack.filters.CourseFilter;
import java.util.UUID;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface CourseService {

    CourseDto insertCourse(CourseDto dto);

    CourseDto getCourse(UUID id);

    Page<CourseDto> searchCourse(Pageable pageable, CourseFilter filter);

    void updateCourse(UUID id, CourseDto dto);

    void deleteCourse(UUID id);
}
