package it.erika.gymtrack.controllers;

import it.erika.gymtrack.dto.SubscriptionDto;
import it.erika.gymtrack.filters.SubscriptionFilter;
import it.erika.gymtrack.services.SubscriptionService;
import jakarta.validation.Valid;
import java.util.UUID;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("subscription")
public class SubscriptionController {

    private final SubscriptionService service;

    public SubscriptionController(SubscriptionService service) {
        this.service = service;
    }

    @PostMapping
    public SubscriptionDto insertSubscription(@RequestBody @Valid SubscriptionDto dto) {
        return service.insertSubscription(dto);
    }

    @GetMapping("{id}")
    public SubscriptionDto getSubscription(@PathVariable(name = "id") UUID id) {
        return service.getSubscription(id);
    }

    @GetMapping
    public Page<SubscriptionDto> searchSubscription(@PageableDefault Pageable pageable, SubscriptionFilter filter) {
        return service.searchSubscription(pageable, filter);
    }

    @PutMapping("{id}")
    public void updateSubscription(@PathVariable(name = "id") UUID id, @RequestBody SubscriptionDto dto) {
        service.updateSubscription(id, dto);
    }

    @DeleteMapping("{id}")
    public void deleteSubscription(@PathVariable(name = "id") UUID id) {
        service.deleteSubscription(id);
    }
}
