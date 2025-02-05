package it.erika.gymtrack.services;

import it.erika.gymtrack.dto.SubscriptionDto;
import it.erika.gymtrack.entities.Subscription;
import it.erika.gymtrack.filters.SubscriptionFilter;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.UUID;

public interface SubscriptionService {

    SubscriptionDto insertSubscription(SubscriptionDto dto);
    SubscriptionDto getSubscription(UUID id);
    Page<SubscriptionDto> searchSubscription(Pageable pageable, SubscriptionFilter filter);
    void updateSubscription(UUID id, SubscriptionDto dto);
    void deleteSubscription(UUID id);
}
