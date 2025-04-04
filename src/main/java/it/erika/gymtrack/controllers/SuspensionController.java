package it.erika.gymtrack.controllers;

import it.erika.gymtrack.dto.SuspensionDto;
import it.erika.gymtrack.filters.SuspensionFilter;
import it.erika.gymtrack.services.SuspensionService;
import jakarta.validation.Valid;
import java.util.UUID;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("suspension")
public class SuspensionController {

    private final SuspensionService service;

    public SuspensionController(SuspensionService service) {
        this.service = service;
    }

    @PostMapping
    public SuspensionDto insertSuspension(@RequestBody @Valid SuspensionDto dto) {
        return service.insertSuspension(dto);
    }

    @GetMapping("{id}")
    public SuspensionDto getSuspension(@PathVariable(name = "id") UUID id) {
        return service.getSuspension(id);
    }

    @GetMapping
    public Page<SuspensionDto> searchSuspension(@PageableDefault Pageable pageable, SuspensionFilter filter) {
        return service.searchSuspension(pageable, filter);
    }

    @PutMapping("{id}")
    public void updateSuspension(@PathVariable(name = "id") UUID id, @RequestBody @Valid SuspensionDto dto) {
        service.updateSuspension(dto, id);
    }

    @DeleteMapping("{id}")
    public void deleteSuspension(@PathVariable(name = "id") UUID id) {
        service.deleteSuspension(id);
    }
}
