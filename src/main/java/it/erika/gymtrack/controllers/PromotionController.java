package it.erika.gymtrack.controllers;

import it.erika.gymtrack.dto.PromotionDto;
import it.erika.gymtrack.exceptions.PromotionNotFoundException;
import it.erika.gymtrack.services.PromotionService;
import it.erika.gymtrack.services.SubscriptionTypeService;
import jakarta.validation.Valid;
import java.util.List;
import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("subscriptionTypes/{subscriptionTypeId}")
public class PromotionController {

    private final PromotionService service;

    private final SubscriptionTypeService subscriptionTypeService;

    public PromotionController(PromotionService service, SubscriptionTypeService subscriptionTypeService) {
        this.service = service;
        this.subscriptionTypeService = subscriptionTypeService;
    }

    @PostMapping("promotions")
    public PromotionDto insertPromotion(
            @PathVariable(name = "subscriptionTypeId") UUID subscriptionTypeId, @RequestBody @Valid PromotionDto dto) {
        subscriptionTypeService.readOneSubscriptionType(subscriptionTypeId);
        return service.insertPromotion(subscriptionTypeId, dto);
    }

    @GetMapping("promotions")
    public List<PromotionDto> getAllPromotions(@PathVariable(name = "subscriptionTypeId") UUID subscriptionTypeId) {
        subscriptionTypeService.readOneSubscriptionType(subscriptionTypeId);
        return service.getAllPromotions(subscriptionTypeId);
    }

    @GetMapping("promotions/{id}")
    public PromotionDto getPromotion(
            @PathVariable(name = "subscriptionTypeId") UUID subscriptionTypeId, @PathVariable(name = "id") UUID id) {
        subscriptionTypeService.readOneSubscriptionType(subscriptionTypeId);
        return service.getPromotion(id);
    }

    @GetMapping("activePromotion")
    public PromotionDto getActive(@PathVariable(name = "subscriptionTypeId") UUID subscriptionTypeId) {
        return service.getActivePromotionBySubscriptionTypeId(subscriptionTypeId)
                        .orElseThrow(() -> new PromotionNotFoundException(HttpStatus.NOT_FOUND, "Promotion not found"));
    }

    @PutMapping("promotions/{id}")
    public void updatePromotion(
            @PathVariable(name = "id") UUID id,
            @PathVariable(name = "subscriptionTypeId") UUID subscriptionTypeId,
            @RequestBody @Valid PromotionDto dto) {
        subscriptionTypeService.readOneSubscriptionType(subscriptionTypeId);
        service.updatePromotion(subscriptionTypeId, id, dto);
    }

    @DeleteMapping("promotions/{id}")
    public void deletePromotion(
            @PathVariable(name = "subscriptionTypeId") UUID subscriptionTypeId, @PathVariable(name = "id") UUID id) {
        subscriptionTypeService.readOneSubscriptionType(subscriptionTypeId);
        service.deletePromotion(id);
    }
}
