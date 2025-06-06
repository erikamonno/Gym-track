package it.erika.gymtrack.services;

import it.erika.gymtrack.dto.SubscriptionDto;
import it.erika.gymtrack.entities.Payment;
import it.erika.gymtrack.entities.Subscription;
import it.erika.gymtrack.entities.SubscriptionType;
import it.erika.gymtrack.enumes.Status;
import it.erika.gymtrack.enumes.Type;
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

import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
@Log4j2
public class SubscriptionServiceImpl implements SubscriptionService {

    private final SubscriptionMapper mapper;
    private final SubscriptionRepository repository;
    private final SubscriptionTypeService subscriptionTypeService;
    private final ReferenceMapper referenceMapper;
    private final PromotionService promotionService;

    public SubscriptionServiceImpl(
            SubscriptionMapper mapper,
            SubscriptionRepository repository,
            SubscriptionTypeService subscriptionTypeService,
            ReferenceMapper referenceMapper, PromotionService promotionService) {
        this.mapper = mapper;
        this.repository = repository;
        this.subscriptionTypeService = subscriptionTypeService;
        this.referenceMapper = referenceMapper;
        this.promotionService = promotionService;
    }

    @Override
    @Transactional
    public SubscriptionDto insertSubscription(SubscriptionDto dto) {
        Subscription entity = new Subscription();
        log.info("Insert subscription {}", dto);
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

        generatePayment(entity);

        entity = repository.save(entity);
        return mapper.toDto(entity);
    }

    private void generatePayment(Subscription entity) {
        var payment = new Payment();
        payment.setType(Type.SUBSCRIPTION);
        payment.setStatus(Status.NOT_DONE);
        payment.setCurrency(entity.getSubscriptionType().getCurrency());

        var amount = computeAmount(entity.getSubscriptionType());

        payment.setAmount(amount);
        entity.addPayment(payment);
    }

    private Double computeAmount(SubscriptionType subscriptionType) {
        Double amount;
        var promotion = promotionService.getActivePromotionBySubscriptionTypeId(subscriptionType.getId());
        if(promotion.isEmpty()) {
            amount = subscriptionType.getAmount();
            log.info("Promotion doesn't exist, amount uses subscriptionType amount with value {}", amount);
        } else {
            amount = promotion.get().getAmount();
            log.info("Promotion exists, amount uses promotion amount with id {} and value {}", promotion.get().getId(), amount);
        }
        return amount;
    }

    @Override
    public SubscriptionDto getSubscription(UUID id) {
        Optional<Subscription> oEntity = repository.findById(id);
        if (oEntity.isEmpty()) {
            throw new SubscriptionNotFoundException(HttpStatus.NOT_FOUND, "Subscription not found");
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
            throw new SubscriptionNotFoundException(HttpStatus.NOT_FOUND, "Subscription not found");
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
