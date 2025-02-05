package it.erika.gymtrack.services;

import it.erika.gymtrack.dto.SubscriptionDto;
import it.erika.gymtrack.filters.SubscriptionFilter;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class SubscriptionServiceImpl implements SubscriptionService {
    @Override
    public SubscriptionDto insertSubscription(SubscriptionDto dto) {
        return null;
    }

    @Override
    public SubscriptionDto getSubscription(UUID id) {
        return null;
    }

    @Override
    public Page<SubscriptionDto> searchSubscription(Pageable pageable, SubscriptionFilter filter) {
        return null;
    }

    @Override
    public void updateSubscription(UUID id, SubscriptionDto dto) {

    }

    @Override
    public void deleteSubscription(UUID id) {

    }
}
