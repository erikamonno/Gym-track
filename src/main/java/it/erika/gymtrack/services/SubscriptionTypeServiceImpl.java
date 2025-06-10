package it.erika.gymtrack.services;

import it.erika.gymtrack.dto.SubscriptionTypeDto;
import it.erika.gymtrack.entities.SubscriptionType;
import it.erika.gymtrack.exceptions.SubscriptionTypeNotFound;
import it.erika.gymtrack.filters.SubscriptionTypeFilter;
import it.erika.gymtrack.mappers.SubscriptionTypeMapper;
import it.erika.gymtrack.repository.SubscriptionTypeRepository;
import it.erika.gymtrack.specifications.SubscriptionTypeSpecification;
import jakarta.transaction.Transactional;
import java.util.Optional;
import java.util.UUID;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
@Log4j2
public class SubscriptionTypeServiceImpl implements SubscriptionTypeService {

    private final SubscriptionTypeMapper mapper;
    private final SubscriptionTypeRepository repository;

    public SubscriptionTypeServiceImpl(SubscriptionTypeMapper mapper, SubscriptionTypeRepository repository) {
        this.mapper = mapper;
        this.repository = repository;
    }

    @Override
    public SubscriptionTypeDto insertSubscriptionType(SubscriptionTypeDto dto) {
        SubscriptionType entity = new SubscriptionType();
        log.info("Insert subscriptionType {}", dto);
        entity.setName(dto.getName());
        entity.setDescription(dto.getDescription());
        entity.setDurationInDays(dto.getDurationInDays());
        entity.setMaxDailyAccesses(dto.getMaxDailyAccesses());
        entity.setCurrency(dto.getCurrency());
        entity.setAmount(dto.getAmount());
        entity = repository.save(entity);
        return mapper.toDto(entity);
    }

    @Override
    public SubscriptionTypeDto readOneSubscriptionType(UUID id) {
        Optional<SubscriptionType> oEntity = repository.findById(id);
        if (oEntity.isEmpty()) {
            throw new SubscriptionTypeNotFound(HttpStatus.NOT_FOUND, "SubscriptionType not found");
        }
        var entity = oEntity.get();
        return mapper.toDto(entity);
    }

    @Override
    public Page<SubscriptionTypeDto> searchSubscriptionType(Pageable pageable, SubscriptionTypeFilter filter) {
        return repository
                .findAll(new SubscriptionTypeSpecification(filter), pageable)
                .map(subscriptionType -> mapper.toDto(subscriptionType));
    }

    @Override
    @Transactional
    public void updateSubscriptionType(SubscriptionTypeDto dto, UUID id) {
        Optional<SubscriptionType> oEntity = repository.findById(id);
        if (oEntity.isEmpty()) {
            throw new SubscriptionTypeNotFound(HttpStatus.NOT_FOUND, "SubscriptionType not found");
        }
        var entity = oEntity.get();
        entity.setName(dto.getName());
        entity.setDescription(dto.getDescription());
        entity.setDurationInDays(dto.getDurationInDays());
        entity.setMaxDailyAccesses(dto.getMaxDailyAccesses());
        entity.setCurrency(dto.getCurrency());
        entity.setAmount(dto.getAmount());
    }

    @Override
    public void deleteSubscriptionType(UUID id) {
        repository.deleteById(id);
    }
}
