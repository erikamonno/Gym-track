package it.erika.gymtrack.services;

import it.erika.gymtrack.dto.CourseDto;
import it.erika.gymtrack.dto.SubscriptionDto;
import it.erika.gymtrack.entities.*;
import it.erika.gymtrack.enumes.Status;
import it.erika.gymtrack.enumes.Type;
import it.erika.gymtrack.exceptions.CourseNotFoundException;
import it.erika.gymtrack.exceptions.CourseNotValidException;
import it.erika.gymtrack.exceptions.SubscriptionNotFoundException;
import it.erika.gymtrack.exceptions.SubscriptionNotValidException;
import it.erika.gymtrack.filters.SubscriptionFilter;
import it.erika.gymtrack.mappers.CourseMapper;
import it.erika.gymtrack.mappers.ReferenceMapper;
import it.erika.gymtrack.mappers.SubscriptionMapper;
import it.erika.gymtrack.repository.SubscriptionRepository;
import it.erika.gymtrack.specifications.SubscriptionSpecification;
import jakarta.transaction.Transactional;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
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
    private final CourseService courseService;
    private final CourseMapper courseMapper;

    public SubscriptionServiceImpl(
            SubscriptionMapper mapper,
            SubscriptionRepository repository,
            SubscriptionTypeService subscriptionTypeService,
            ReferenceMapper referenceMapper,
            PromotionService promotionService, CourseService courseService, CourseMapper courseMapper) {
        this.mapper = mapper;
        this.repository = repository;
        this.subscriptionTypeService = subscriptionTypeService;
        this.referenceMapper = referenceMapper;
        this.promotionService = promotionService;
        this.courseService = courseService;
        this.courseMapper = courseMapper;
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
        Double amount;
        var onlyNewCustomer = isNewCustomer(entity.getCustomer().getId());
        var promotion = promotionService.getActivePromotionBySubscriptionTypeId(
                entity.getSubscriptionType().getId(), onlyNewCustomer);
        payment.setType(Type.SUBSCRIPTION);
        payment.setStatus(Status.NOT_DONE);
        payment.setCurrency(entity.getSubscriptionType().getCurrency());

        if (promotion.isEmpty()) {
            amount = entity.getSubscriptionType().getAmount();
            log.info("Promotion doesn't exist, amount uses subscriptionType amount with value {}", amount);
        } else {
            amount = promotion.get().getAmount();
            log.info(
                    "Promotion exists, amount uses promotion amount with id {} and value {}",
                    promotion.get().getId(),
                    amount);
            entity.setPromotion(referenceMapper.toPromotion(promotion.get().getId()));
        }
        payment.setAmount(amount);

        entity.addPayment(payment);
    }

    private boolean isNewCustomer(UUID customerId) {
        var filter = new SubscriptionFilter();
        filter.setCustomerId(customerId);
        return repository.exists(new SubscriptionSpecification(filter));
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

    @Override
    @Transactional
    public void replaceCourses(UUID id, List<UUID> courseIdList) {
        log.info("Start replacing courses for subscription id {}, courses: {}", id, courseIdList);
        log.debug("Finding subscription with id {}", id);
        Optional<Subscription> oEntity = repository.findById(id);
        if(oEntity.isEmpty()) {
            log.warn("Subscription not found with id {}", id);
            throw new SubscriptionNotFoundException(HttpStatus.NOT_FOUND, "Subscription not found");
        }
        var subscription = oEntity.get();
        log.debug("Subscription found with id {}, removing previous courses", id);
        subscription.getSubscriptionCourses().clear();
        for(UUID courseId : courseIdList) {
            log.debug("Finding course with id {} from {}", courseId, courseIdList);

            var courseDto = getCourseOrThrow(courseId);

            checkValidCourses(courseDto);

            var subscriptionCourse = new SubscriptionCourse();
            subscriptionCourse.setId(new SubscriptionCourseKey());
            subscriptionCourse.setCourse(referenceMapper.toCourse(courseId));  //non setto l'id perchè c'è già l'annotation mapsId
            subscription.addSubscriptionCourse(subscriptionCourse);
            log.debug("Course added {}", courseDto);
        }
        log.info("Courses successfully replaced for subscription with id {}", id);
    }

    private void checkValidCourses(CourseDto courseDto) {
        var today = Instant.now();
        var isValidCourse = today.isAfter(courseDto.getValidFrom())
                || today.equals(courseDto.getValidFrom()) && today.isBefore(courseDto.getValidTo());
        if (!isValidCourse) {
            throw new CourseNotValidException(HttpStatus.BAD_REQUEST, "Course not valid");
        }
    }

    @Override
    public List<CourseDto> getCourses(UUID id) {
        List<CourseDto> coursesList = new ArrayList<>();
        log.debug("Finding subscription with id {}", id);
        Optional<Subscription> oEntity = repository.findById(id);
        if(oEntity.isEmpty()) {
            throw new SubscriptionNotFoundException(HttpStatus.NOT_FOUND, "Subscription not found");
        }
        var subscription = oEntity.get();
        var subscriptionCourses = subscription.getSubscriptionCourses();
        for(SubscriptionCourse subscriptionCourse : subscriptionCourses) {
            var course = subscriptionCourse.getCourse();
            coursesList.add(courseMapper.toDto(course));
        }
        return coursesList;
    }

    private CourseDto getCourseOrThrow(UUID courseId) {
        try{
            return courseService.getCourse(courseId);
        } catch (CourseNotFoundException e) {
            throw new CourseNotFoundException(HttpStatus.BAD_REQUEST, "Course id not found with id %s".formatted(courseId));
        }
    }
}


