package it.erika.gymtrack.controllers;

import it.erika.gymtrack.dto.PromotionDto;
import it.erika.gymtrack.services.PromotionService;
import it.erika.gymtrack.services.SubscriptionTypeService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("subscriptionTypes/{subscriptionTypeId}/promotions")
public class PromotionController {

    private final PromotionService service;

    private final SubscriptionTypeService subscriptionTypeService;

    public PromotionController(PromotionService service, SubscriptionTypeService subscriptionTypeService) {
        this.service = service;
        this.subscriptionTypeService = subscriptionTypeService;
    }

    @PostMapping
    public PromotionDto insertPromotion(@PathVariable(name = "subscriptionTypeId") UUID subscriptionTypeId, @RequestBody @Valid PromotionDto dto) {
        subscriptionTypeService.readOneSubscriptionType(subscriptionTypeId);
        return service.insertPromotion(subscriptionTypeId, dto);
    }

    @GetMapping
    public List<PromotionDto> getAllPromotions(@PathVariable(name = "subscriptionTypeId") UUID subscriptionTypeId) {
        subscriptionTypeService.readOneSubscriptionType(subscriptionTypeId);
        return service.getAllPromotions(subscriptionTypeId);
    }

    @GetMapping("{id}")
    public PromotionDto getPromotion(@PathVariable(name = "subscriptionTypeId") UUID subscriptionTypeId, @PathVariable(name = "id") UUID id) {
        subscriptionTypeService.readOneSubscriptionType(subscriptionTypeId);
        return service.getPromotion(id);
    }

    @PutMapping("{id}")
    public void updatePromotion(@PathVariable(name = "id") UUID id, @PathVariable(name = "subscriptionTypeId") UUID subscriptionTypeId, @RequestBody @Valid PromotionDto dto) {
        subscriptionTypeService.readOneSubscriptionType(subscriptionTypeId);
        service.updatePromotion(subscriptionTypeId, id, dto);
    }

    @DeleteMapping("{id}")
    public void deletePromotion(@PathVariable(name = "subscriptionTypeId") UUID subscriptionTypeId, @PathVariable(name = "id") UUID id) {
        subscriptionTypeService.readOneSubscriptionType(subscriptionTypeId);
        service.deletePromotion(id);
    }
}
