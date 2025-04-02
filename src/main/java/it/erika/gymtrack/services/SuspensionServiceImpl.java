package it.erika.gymtrack.services;

import it.erika.gymtrack.dto.SubscriptionDto;
import it.erika.gymtrack.dto.SuspensionDto;
import it.erika.gymtrack.entities.Suspension;
import it.erika.gymtrack.exceptions.SuspensionAlreadyExistInThatDateException;
import it.erika.gymtrack.exceptions.SuspensionNotFoundException;
import it.erika.gymtrack.filters.SuspensionFilter;
import it.erika.gymtrack.mappers.SubscriptionMapper;
import it.erika.gymtrack.mappers.SuspensionMapper;
import it.erika.gymtrack.repository.SuspensionRepository;
import it.erika.gymtrack.specifications.ActiveSuspensionSpecification;
import it.erika.gymtrack.specifications.SuspensionSpecification;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.Duration;
import java.time.Instant;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
@Service
public class SuspensionServiceImpl implements SuspensionService{

    private final SuspensionRepository repository;
    private final SuspensionMapper mapper;
    private final SubscriptionService subscriptionService;
    private final SubscriptionMapper subscriptionMapper;

    public SuspensionServiceImpl(SuspensionRepository repository, SuspensionMapper mapper, SubscriptionService subscriptionService, SubscriptionMapper subscriptionMapper) {
        this.repository = repository;
        this.mapper = mapper;
        this.subscriptionService = subscriptionService;
        this.subscriptionMapper = subscriptionMapper;
    }

    @Override
    public SuspensionDto insertSuspension(SuspensionDto dto) {
        Suspension entity = new Suspension();
        var subscriptionDto = subscriptionService.getSubscription(dto.getSubscription().getId());

        checkActiveSuspensionAtInstant(dto.getSubscription().getId(), dto.getStartDate());

        entity.setStartDate(dto.getStartDate());
        entity.setEndDate(dto.getEndDate());
        entity.setReason(dto.getReason());
        entity.setNote(dto.getNote());
        entity.setSubscription(subscriptionMapper.toEntity(subscriptionDto));
        entity.setRefundSuspension(dto.getRefundSuspension());
        extendSubscription(subscriptionDto, entity);

        entity = repository.save(entity);
        return mapper.toDto(entity);
    }

    private void extendSubscription(SubscriptionDto subscriptionDto, Suspension entity) {
        if(entity.getRefundSuspension()) {
            var originalSubscriptionEndDate = subscriptionDto.getEndDate();
            var suspensionDuration = Duration.between(entity.getStartDate(), entity.getEndDate());
            var newEndDate = originalSubscriptionEndDate.plus(suspensionDuration);
            entity.setOriginalSubscriptionEndDate(originalSubscriptionEndDate);
            entity.getSubscription().setEndDate(newEndDate);
        }
    }

    @Override
    public void checkActiveSuspensionAtInstant(UUID subscriptionId, Instant atDate) {
        List<Suspension> suspensionList = repository.findAll(new ActiveSuspensionSpecification(subscriptionId, atDate));
        if(!suspensionList.isEmpty()) {
            throw new SuspensionAlreadyExistInThatDateException("Exisiting suspension");
        }
    }

    @Override
    public SuspensionDto getSuspension(UUID id) {
        Optional<Suspension> oEntity = repository.findById(id);
        if(oEntity.isEmpty()) {
            throw new SuspensionNotFoundException("Suspension not found");
        }
        var entity = oEntity.get();
        return mapper.toDto(entity);
    }

    @Override
    public Page<SuspensionDto> searchSuspension(Pageable pageable, SuspensionFilter filter) {
        return repository.findAll(new SuspensionSpecification(filter), pageable).map(suspension -> mapper.toDto(suspension));
    }

    @Transactional
    @Override
    public void updateSuspension(SuspensionDto dto, UUID id) {
        Optional<Suspension> oEntity = repository.findById(id);
        if(oEntity.isEmpty()) {
            throw new SuspensionNotFoundException("Suspension not found");
        }
        var entity = oEntity.get();
        var subscriptionDto = subscriptionService.getSubscription(dto.getSubscription().getId());
        entity.setStartDate(dto.getStartDate());
        entity.setEndDate(dto.getEndDate());
        entity.setReason(dto.getReason());
        entity.setNote(dto.getNote());
        entity.setSubscription(subscriptionMapper.toEntity(subscriptionDto));
        entity.setRefundSuspension(dto.getRefundSuspension());
        if(dto.getRefundSuspension()) {
            Instant originalSubscriptionEndDate = null;
            if(entity.getOriginalSubscriptionEndDate() != null) {
                originalSubscriptionEndDate = entity.getOriginalSubscriptionEndDate();
            } else {
                originalSubscriptionEndDate = entity.getSubscription().getEndDate();
            }
            var suspensionDuration = Duration.between(dto.getStartDate(), dto.getEndDate());
            var newEndDate = originalSubscriptionEndDate.plus(suspensionDuration);
            entity.setOriginalSubscriptionEndDate(originalSubscriptionEndDate);
            entity.getSubscription().setEndDate(newEndDate);
        } else {
            if(entity.getOriginalSubscriptionEndDate() != null) {
                Instant originalEndDate = entity.getOriginalSubscriptionEndDate();
                entity.getSubscription().setEndDate(originalEndDate);
                entity.setOriginalSubscriptionEndDate(null);
            }
        }
    }

    @Override
    public void deleteSuspension(UUID id) {
        repository.deleteById(id);
    }
}
