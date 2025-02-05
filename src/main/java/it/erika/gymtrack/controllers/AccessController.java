package it.erika.gymtrack.controllers;

import it.erika.gymtrack.dto.AccessDto;
import it.erika.gymtrack.filters.AccessFilter;
import it.erika.gymtrack.services.AccessService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("access")
public class AccessController {

    private final AccessService service;

    public AccessController(AccessService service) {
        this.service = service;
    }

    @PostMapping
    public AccessDto insertAccess(@RequestBody @Valid AccessDto dto) {
        return service.insertAccess(dto);
    }

    @GetMapping("{id}")
    public AccessDto getAccess(@PathVariable(name = "id")UUID id) {
        return service.getAccess(id);
    }

    @GetMapping
    public Page<AccessDto> searchAccess(@PageableDefault Pageable pageable, AccessFilter filter) {
        return service.searchAccess(pageable, filter);
    }

    @PutMapping("{id}")
    public void updateAccess(@RequestBody AccessDto dto, @PathVariable(name = "id") UUID id) {
        service.updateAccess(id, dto);
    }

    @DeleteMapping("{id}")
    public void deleteAccess(@PathVariable(name = "id") UUID id) {
        service.deleteAccess(id);
    }
}
