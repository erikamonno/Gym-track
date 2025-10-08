package it.erika.gymtrack.services;

import it.erika.gymtrack.dto.CourseDto;
import it.erika.gymtrack.entities.Course;
import it.erika.gymtrack.exceptions.CourseNotFoundException;
import it.erika.gymtrack.filters.CourseFilter;
import it.erika.gymtrack.mappers.CourseMapper;
import it.erika.gymtrack.repository.CourseRepository;
import it.erika.gymtrack.specifications.CourseSpecification;
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
public class CourseServiceImpl implements CourseService {

    private final CourseMapper mapper;
    private final CourseRepository repository;

    public CourseServiceImpl(CourseMapper mapper, CourseRepository repository) {
        this.mapper = mapper;
        this.repository = repository;
    }

    @Override
    public CourseDto insertCourse(CourseDto dto) {
        Course entity = new Course();
        log.info("Insert new course with {}", dto);
        entity.setName(dto.getName());
        entity.setValidFrom(dto.getValidFrom());
        entity.setValidTo(dto.getValidTo());
        entity = repository.save(entity);
        return mapper.toDto(entity);
    }

    @Override
    public CourseDto getCourse(UUID id) {
        Optional<Course> oEntity = repository.findById(id);
        if (oEntity.isEmpty()) {
            throw new CourseNotFoundException(HttpStatus.NOT_FOUND, "Course not found");
        }
        Course entity = oEntity.get();
        return mapper.toDto(entity);
    }

    @Override
    public Page<CourseDto> searchCourse(Pageable pageable, CourseFilter filter) {
        return repository.findAll(new CourseSpecification(filter), pageable).map(course -> mapper.toDto(course));
    }

    @Override
    @Transactional
    public void updateCourse(UUID id, CourseDto dto) {
        Optional<Course> oEntity = repository.findById(id);
        if (oEntity.isEmpty()) {
            throw new CourseNotFoundException(HttpStatus.NOT_FOUND, "Course not found");
        }
        var entity = oEntity.get();
        entity.setName(dto.getName());
        entity.setValidFrom(dto.getValidFrom());
        entity.setValidTo(dto.getValidTo());
    }

    @Override
    public void deleteCourse(UUID id) {
        repository.deleteById(id);
    }
}
