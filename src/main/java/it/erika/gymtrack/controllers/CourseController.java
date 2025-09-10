package it.erika.gymtrack.controllers;

import it.erika.gymtrack.dto.CourseDto;
import it.erika.gymtrack.filters.CourseFilter;
import it.erika.gymtrack.services.CourseService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("courses") //plurale perchè l'url si riferisce a più risorse dello stesso tipo ossia più corsi
public class CourseController {

    private final CourseService service;

    public CourseController(CourseService service) {
        this.service = service;
    }

    @PostMapping
    public CourseDto insertCourse(@Valid @RequestBody CourseDto dto){
        return service.insertCourse(dto);
    }

    @GetMapping("{id}")
    public CourseDto getCourse(@PathVariable(name = "id") UUID id) {
        return service.getCourse(id);
    }

    @GetMapping
    public Page<CourseDto> searchCourse(@PageableDefault Pageable pageable, CourseFilter filter) {
        return service.searchCourse(pageable, filter);
    }

    @PutMapping("{id}")
    public void updateCourse(@Valid @RequestBody CourseDto dto, @PathVariable(name = "id") UUID id) {
        service.updateCourse(id, dto);
    }

    @DeleteMapping("{id}")
    public void deleteCourse(@PathVariable(name = "id") UUID id) {
        service.deleteCourse(id);
    }
}
