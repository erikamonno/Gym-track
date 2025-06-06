package it.erika.gymtrack.services;

import it.erika.gymtrack.dto.PromotionDto;
import it.erika.gymtrack.entities.Promotion;
import it.erika.gymtrack.entities.SubscriptionType;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface PromotionService {

    PromotionDto insertPromotion(UUID subscriptionTypeId, PromotionDto dto);
    List<PromotionDto> getAllPromotions(UUID subscriptionTypeId);
    PromotionDto getPromotion(UUID id);
    Optional<PromotionDto> getActivePromotionBySubscriptionTypeId(UUID subscriptionTypeId);
    void updatePromotion(UUID subscriptionTypeId, UUID id, PromotionDto dto);
    void deletePromotion(UUID id);
}
