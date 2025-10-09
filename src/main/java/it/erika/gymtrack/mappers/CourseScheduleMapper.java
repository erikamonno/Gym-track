package it.erika.gymtrack.mappers;

import it.erika.gymtrack.dto.CourseScheduleDto;
import it.erika.gymtrack.entities.CourseSchedule;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CourseScheduleMapper {

    CourseScheduleDto toDto(CourseSchedule entity);

    CourseSchedule toEntity(CourseScheduleDto dto);
}
