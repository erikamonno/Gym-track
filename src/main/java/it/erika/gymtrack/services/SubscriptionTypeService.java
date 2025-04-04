package it.erika.gymtrack.services;

import it.erika.gymtrack.dto.SubscriptionTypeDto;
import it.erika.gymtrack.filters.SubscriptionTypeFilter;
import java.util.UUID;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface SubscriptionTypeService {

    SubscriptionTypeDto insertSubscriptionType(SubscriptionTypeDto dto);

    SubscriptionTypeDto readOneSubscriptionType(UUID id);

    Page<SubscriptionTypeDto> searchSubscriptionType(Pageable pageable, SubscriptionTypeFilter filter);

    void updateSubscriptionType(SubscriptionTypeDto dto, UUID id);

    void deleteSubscriptionType(UUID id);
}
