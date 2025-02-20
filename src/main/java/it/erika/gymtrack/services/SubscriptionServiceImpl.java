package it.erika.gymtrack.services;

import it.erika.gymtrack.dto.SubscriptionDto;
import it.erika.gymtrack.entities.Subscription;
import it.erika.gymtrack.exceptions.SubscriptionNotFoundException;
import it.erika.gymtrack.filters.SubscriptionFilter;
import it.erika.gymtrack.mappers.CustomerMapper;
import it.erika.gymtrack.mappers.SubscriptionMapper;
import it.erika.gymtrack.mappers.SubscriptionTypeMapper;
import it.erika.gymtrack.repository.SubscriptionRepository;
import it.erika.gymtrack.repository.SubscriptionTypeRepository;
import it.erika.gymtrack.specifications.SubscriptionSpecification;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
public class SubscriptionServiceImpl implements SubscriptionService {

    private final SubscriptionMapper mapper;
    private final SubscriptionRepository repository;
    private final CustomerService customerService;
    private final CustomerMapper customerMapper;
    private final SubscriptionTypeService subscriptionTypeService;
    private final SubscriptionTypeMapper subscriptionTypeMapper;

    public SubscriptionServiceImpl(SubscriptionMapper mapper, SubscriptionRepository repository, CustomerService customerService, CustomerMapper customerMapper, SubscriptionTypeService subscriptionTypeService, SubscriptionTypeMapper subscriptionTypeMapper) {
        this.mapper = mapper;
        this.repository = repository;
        this.customerService = customerService;
        this.customerMapper = customerMapper;
        this.subscriptionTypeService = subscriptionTypeService;
        this.subscriptionTypeMapper = subscriptionTypeMapper;
    }

    @Override
    public SubscriptionDto insertSubscription(SubscriptionDto dto) {
        Subscription entity = new Subscription();
        var customerDto = customerService.getCustomer(dto.getCustomer().getId());
        var subscriptionTypeDto = subscriptionTypeService.readOneSubscriptionType(dto.getSubscriptionType().getId());
        entity.setStartDate(dto.getStartDate());
        entity.setEndDate(dto.getEndDate());
        entity.setSubscriptionType(subscriptionTypeMapper.toEntity(subscriptionTypeDto));
        entity.setCustomer(customerMapper.toEntity(customerDto));
        entity = repository.save(entity);
        return mapper.toDto(entity);
    }

    @Override
    public SubscriptionDto getSubscription(UUID id) {
        Optional<Subscription> oEntity = repository.findById(id);
        if(oEntity.isEmpty()) {
            throw new SubscriptionNotFoundException("Subscription not found");
        }
        var entity = oEntity.get();
        return mapper.toDto(entity);
    }

    @Override
    public Page<SubscriptionDto> searchSubscription(Pageable pageable, SubscriptionFilter filter) {
        return repository.findAll(new SubscriptionSpecification(filter), pageable).map(subscription -> mapper.toDto(subscription));
    }

    @Override
    @Transactional
    public void updateSubscription(UUID id, SubscriptionDto dto) {
        Optional<Subscription> oEntity = repository.findById(id);
        if(oEntity.isEmpty()) {
            throw new SubscriptionNotFoundException("Subscription not found");
        }
        var entity = oEntity.get();
        var customerDto = customerService.getCustomer(dto.getCustomer().getId());
        var subscriptionTypeDto = subscriptionTypeService.readOneSubscriptionType(dto.getSubscriptionType().getId());
        entity.setStartDate(dto.getStartDate());
        entity.setEndDate(dto.getEndDate());
        entity.setSubscriptionType(subscriptionTypeMapper.toEntity(subscriptionTypeDto));
        entity.setCustomer(customerMapper.toEntity(customerDto));
    }

    @Override
    public void deleteSubscription(UUID id) {
        repository.deleteById(id);
    }
}
