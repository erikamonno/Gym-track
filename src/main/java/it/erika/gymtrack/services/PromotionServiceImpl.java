package it.erika.gymtrack.services;

import it.erika.gymtrack.dto.PromotionDto;
import it.erika.gymtrack.entities.Promotion;
import it.erika.gymtrack.exceptions.PromotionDateNotValidException;
import it.erika.gymtrack.exceptions.PromotionNotFoundException;
import it.erika.gymtrack.exceptions.PromotionOverlappingException;
import it.erika.gymtrack.mappers.PromotionMapper;
import it.erika.gymtrack.mappers.ReferenceMapper;
import it.erika.gymtrack.repository.PromotionRepository;
import it.erika.gymtrack.specifications.ActivePromotionSpecification;
import it.erika.gymtrack.specifications.OverlappingPromotionSpecification;
import jakarta.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
@Log4j2
public class PromotionServiceImpl implements PromotionService {

    private final PromotionRepository repository;
    private final ReferenceMapper referenceMapper;
    private final PromotionMapper mapper;

    public PromotionServiceImpl(
            PromotionRepository repository, ReferenceMapper referenceMapper, PromotionMapper mapper) {
        this.repository = repository;
        this.referenceMapper = referenceMapper;
        this.mapper = mapper;
    }

    @Override
    public PromotionDto insertPromotion(UUID subscriptionTypeId, PromotionDto dto) {
        Promotion entity = new Promotion();
        log.info("Insert promotion with {} and {}", dto, subscriptionTypeId);
        entity.setName(dto.getName());
        entity.setAmount(dto.getAmount());

        checkValidDate(dto);

        checkRangeDate(dto, subscriptionTypeId, null);

        entity.setValidTo(dto.getValidTo());
        entity.setValidFrom(dto.getValidFrom());
        entity.setSubscriptionType(referenceMapper.toSubscriptionType(subscriptionTypeId));
        entity = repository.save(entity);
        return mapper.toDto(entity);
    }

    private static void checkValidDate(PromotionDto dto) {
        if (dto.getValidTo().isBefore(dto.getValidFrom())) {
            throw new PromotionDateNotValidException(HttpStatus.BAD_REQUEST, "ValidDateTo is before validDateFrom");
        }
    }

    private void checkRangeDate(PromotionDto dto, UUID subscriptionTypeId, UUID id) {
        var existsOverlappingPromotions = repository.exists(
                new OverlappingPromotionSpecification(id, subscriptionTypeId, dto.getValidTo(), dto.getValidFrom()));
        if (existsOverlappingPromotions) {
            throw new PromotionOverlappingException(HttpStatus.CONFLICT, "Promotion overlapping in those dates");
        }
    }

    @Override
    public List<PromotionDto> getAllPromotions(UUID subscriptionTypeId) {
        log.info("All promotions with subscriptionTypeId {}", subscriptionTypeId);
        return repository.findBySubscriptionTypeId(subscriptionTypeId).stream()
                .map(promotion -> mapper.toDto(promotion))
                .toList();
    }

    @Override
    public PromotionDto getPromotion(UUID id) {
        var oEntity = repository.findById(id);
        if (oEntity.isEmpty()) {
            throw new PromotionNotFoundException(HttpStatus.NOT_FOUND, "Promotion not found");
        }
        var entity = oEntity.get();
        return mapper.toDto(entity);
    }

    @Override
    public Optional<PromotionDto> getActivePromotionBySubscriptionTypeId(UUID subscriptionTypeId) {
        var activePromotion = repository.findOne(new ActivePromotionSpecification(subscriptionTypeId));
        return activePromotion.map(promotion -> mapper.toDto(promotion));
    }

    @Override
    @Transactional
    public void updatePromotion(UUID subscriptionTypeId, UUID id, PromotionDto dto) {
        var oEntity = repository.findById(id);
        if (oEntity.isEmpty()) {
            throw new PromotionNotFoundException(HttpStatus.NOT_FOUND, "Promotion not found");
        }
        var entity = oEntity.get();
        entity.setName(dto.getName());
        entity.setAmount(dto.getAmount());

        checkValidDate(dto);

        checkRangeDate(dto, subscriptionTypeId, id);

        entity.setValidTo(dto.getValidTo());
        entity.setValidFrom(dto.getValidFrom());
        entity.setSubscriptionType(referenceMapper.toSubscriptionType(subscriptionTypeId));
    }

    @Override
    public void deletePromotion(UUID id) {
        repository.deleteById(id);
    }
}
