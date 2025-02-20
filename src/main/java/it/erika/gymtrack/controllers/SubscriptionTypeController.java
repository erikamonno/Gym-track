package it.erika.gymtrack.controllers;

import it.erika.gymtrack.dto.SubscriptionDto;
import it.erika.gymtrack.dto.SubscriptionTypeDto;
import it.erika.gymtrack.entities.SubscriptionType;
import it.erika.gymtrack.filters.SubscriptionTypeFilter;
import it.erika.gymtrack.mappers.SubscriptionTypeMapper;
import it.erika.gymtrack.repository.SubscriptionTypeRepository;
import it.erika.gymtrack.services.SubscriptionTypeService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("subscription_type")

public class SubscriptionTypeController {

    private final SubscriptionTypeService service;

    public SubscriptionTypeController(SubscriptionTypeService service) {
        this.service = service;
    }

    @PostMapping
    public SubscriptionTypeDto insertSubscriptionType(@RequestBody @Valid SubscriptionTypeDto dto) {
        return service.insertSubscriptionType(dto);
    }

    @GetMapping("{id}")
    public SubscriptionTypeDto readOneSubscriptionType(@PathVariable(name = "id")UUID id) {
        return service.readOneSubscriptionType(id);
    }

    @GetMapping
    Page<SubscriptionTypeDto> searchSubscriptionType(@PageableDefault Pageable pageable, SubscriptionTypeFilter filter) {
        return service.searchSubscriptionType(pageable, filter);
    }

    @PutMapping("{id}")
    public void updateSubscriptionType(@RequestBody @Valid SubscriptionTypeDto dto, @RequestParam(name = "id")UUID id) {
        service.updateSubscriptionType(dto, id);
    }

    @DeleteMapping("{id}")
    public void deleteSubscriptionType(@RequestParam UUID id) {
        service.deleteSubscriptionType(id);
    }
}
