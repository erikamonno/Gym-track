package it.erika.gymtrack.controllers;

import it.erika.gymtrack.dto.CourseScheduleDto;
import it.erika.gymtrack.filters.CourseScheduleFilter;
import it.erika.gymtrack.services.CourseScheduleService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("courseSchedules")  //plurale perchè l'url si riferisce a più risorse dello stesso tipo ossia più orari dei corsi
public class CourseScheduleController {

    private final CourseScheduleService service;

    public CourseScheduleController(CourseScheduleService service) {
        this.service = service;
    }

    @PostMapping
    public CourseScheduleDto insertCourseSchedule(@Valid @RequestBody CourseScheduleDto dto) {
        return service.insertCourseSchedule(dto);
    }

    @GetMapping("{id}")
    public CourseScheduleDto getCourseSchedule(@PathVariable(name = "id") UUID id) {
        return service.getCourseSchedule(id);
    }

    @GetMapping
    public Page<CourseScheduleDto> searchCourseSchedule(@PageableDefault Pageable pageable, CourseScheduleFilter filter) {
        return service.searchCourseSchedule(pageable, filter);
    }

    @PutMapping("{id}")
    public void updateCourseSchedule(@Valid @RequestBody CourseScheduleDto dto, @PathVariable(name = "id") UUID id) {
        service.updateCourseSchedule(id, dto);
    }

    @DeleteMapping("{id}")
    public void deleteCourseSchedule(@PathVariable(name = "id") UUID id) {
        service.deleteCourseSchedule(id);
    }
}
