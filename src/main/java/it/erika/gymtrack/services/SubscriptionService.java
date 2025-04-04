package it.erika.gymtrack.services;

import it.erika.gymtrack.dto.SubscriptionDto;
import it.erika.gymtrack.filters.SubscriptionFilter;
import java.util.UUID;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface SubscriptionService {

    SubscriptionDto insertSubscription(SubscriptionDto dto);

    SubscriptionDto getSubscription(UUID id);

    Page<SubscriptionDto> searchSubscription(Pageable pageable, SubscriptionFilter filter);

    void updateSubscription(UUID id, SubscriptionDto dto);

    void deleteSubscription(UUID id);
}
