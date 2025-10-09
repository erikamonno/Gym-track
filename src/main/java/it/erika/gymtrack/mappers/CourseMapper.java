package it.erika.gymtrack.mappers;

import it.erika.gymtrack.dto.CourseDto;
import it.erika.gymtrack.entities.Course;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CourseMapper {

    CourseDto toDto(Course entity);

    Course toEntity(CourseDto dto);
}
