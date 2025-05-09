package it.erika.gymtrack.services;

import it.erika.gymtrack.dto.SubscriptionDto;
import it.erika.gymtrack.entities.Subscription;
import it.erika.gymtrack.exceptions.SubscriptionNotFoundException;
import it.erika.gymtrack.filters.SubscriptionFilter;
import it.erika.gymtrack.mappers.ReferenceMapper;
import it.erika.gymtrack.mappers.SubscriptionMapper;
import it.erika.gymtrack.repository.SubscriptionRepository;
import it.erika.gymtrack.specifications.SubscriptionSpecification;
import jakarta.transaction.Transactional;
import java.time.temporal.ChronoUnit;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class SubscriptionServiceImpl implements SubscriptionService {

    private final SubscriptionMapper mapper;
    private final SubscriptionRepository repository;
    private final SubscriptionTypeService subscriptionTypeService;
    private final ReferenceMapper referenceMapper;

    public SubscriptionServiceImpl(
            SubscriptionMapper mapper,
            SubscriptionRepository repository,
            SubscriptionTypeService subscriptionTypeService,
            ReferenceMapper referenceMapper) {
        this.mapper = mapper;
        this.repository = repository;
        this.subscriptionTypeService = subscriptionTypeService;
        this.referenceMapper = referenceMapper;
    }

    @Override
    @Transactional
    public SubscriptionDto insertSubscription(SubscriptionDto dto) {
        Subscription entity = new Subscription();
        var subscriptionTypeDto = subscriptionTypeService.readOneSubscriptionType(
                dto.getSubscriptionType().getId());
        if (dto.getEndDate() == null) {
            var endDate = dto.getStartDate().plus(subscriptionTypeDto.getDurationInDays(), ChronoUnit.DAYS);
            entity.setEndDate(endDate);
        } else {
            entity.setEndDate(dto.getEndDate());
        }
        entity.setStartDate(dto.getStartDate());
        entity.setSubscriptionType(
                referenceMapper.toSubscriptionType(dto.getSubscriptionType().getId()));
        entity.setCustomer(referenceMapper.toCustomer(dto.getCustomer().getId()));
        entity = repository.save(entity);
        return mapper.toDto(entity);
    }

    @Override
    public SubscriptionDto getSubscription(UUID id) {
        Optional<Subscription> oEntity = repository.findById(id);
        if (oEntity.isEmpty()) {
            throw new SubscriptionNotFoundException("Subscription not found");
        }
        var entity = oEntity.get();
        return mapper.toDto(entity);
    }

    @Override
    public Page<SubscriptionDto> searchSubscription(Pageable pageable, SubscriptionFilter filter) {
        return repository
                .findAll(new SubscriptionSpecification(filter), pageable)
                .map(subscription -> mapper.toDto(subscription));
    }

    @Override
    @Transactional
    public void updateSubscription(UUID id, SubscriptionDto dto) {
        Optional<Subscription> oEntity = repository.findById(id);
        if (oEntity.isEmpty()) {
            throw new SubscriptionNotFoundException("Subscription not found");
        }
        var entity = oEntity.get();
        entity.setStartDate(dto.getStartDate());
        entity.setEndDate(dto.getEndDate());
        entity.setSubscriptionType(referenceMapper.toSubscriptionType(dto.getId()));
        entity.setCustomer(referenceMapper.toCustomer(dto.getCustomer().getId()));
    }

    @Override
    public void deleteSubscription(UUID id) {
        repository.deleteById(id);
    }
}
