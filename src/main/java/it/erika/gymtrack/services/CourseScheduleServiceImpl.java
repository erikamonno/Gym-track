package it.erika.gymtrack.services;

import it.erika.gymtrack.dto.CourseScheduleDto;
import it.erika.gymtrack.entities.CourseSchedule;
import it.erika.gymtrack.exceptions.CourseScheduleNotFoundException;
import it.erika.gymtrack.filters.CourseScheduleFilter;
import it.erika.gymtrack.mappers.CourseScheduleMapper;
import it.erika.gymtrack.mappers.ReferenceMapper;
import it.erika.gymtrack.repository.CourseScheduleRepository;
import it.erika.gymtrack.specifications.CourseScheduleSpecification;
import jakarta.transaction.Transactional;
import java.util.Optional;
import java.util.UUID;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Log4j2
@Service
public class CourseScheduleServiceImpl implements CourseScheduleService {

    private final CourseScheduleRepository repository;
    private final CourseScheduleMapper mapper;
    private final ReferenceMapper referenceMapper;

    public CourseScheduleServiceImpl(
            CourseScheduleRepository repository, CourseScheduleMapper mapper, ReferenceMapper referenceMapper) {
        this.repository = repository;
        this.mapper = mapper;
        this.referenceMapper = referenceMapper;
    }

    @Override
    public CourseScheduleDto insertCourseSchedule(CourseScheduleDto dto) {
        CourseSchedule entity = new CourseSchedule();
        log.info("Insert course schedule with dto {}", dto);
        entity.setDay(dto.getDay());
        entity.setStartTime(dto.getStartTime());
        entity.setEndTime(dto.getEndTime());
        entity.setCourse(referenceMapper.toCourse(dto.getCourse().getId()));
        entity = repository.save(entity);
        log.info(
                "Inserted a course schedule with id {} successfully",
                dto.getCourse().getId());
        return mapper.toDto(entity);
    }

    @Override
    public CourseScheduleDto getCourseSchedule(UUID id) {
        Optional<CourseSchedule> oEntity = repository.findById(id);
        if (oEntity.isEmpty()) {
            throw new CourseScheduleNotFoundException(HttpStatus.NOT_FOUND, "Course schedule not found");
        }
        var entity = oEntity.get();
        return mapper.toDto(entity);
    }

    @Override
    public Page<CourseScheduleDto> searchCourseSchedule(Pageable pageable, CourseScheduleFilter filter) {
        return repository
                .findAll(new CourseScheduleSpecification(filter), pageable)
                .map(courseSchedule -> mapper.toDto(courseSchedule));
    }

    @Override
    @Transactional
    public void updateCourseSchedule(UUID id, CourseScheduleDto dto) {
        Optional<CourseSchedule> oEntity = repository.findById(id);
        if (oEntity.isEmpty()) {
            throw new CourseScheduleNotFoundException(HttpStatus.NOT_FOUND, "Course schedule not found");
        }
        var entity = oEntity.get();
        entity.setDay(dto.getDay());
        entity.setStartTime(dto.getStartTime());
        entity.setEndTime(dto.getEndTime());
    }

    @Override
    public void deleteCourseSchedule(UUID id) {
        repository.deleteById(id);
    }
}
