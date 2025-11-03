package it.erika.gymtrack.services;

import it.erika.gymtrack.configurations.GymScheduleProperties;
import it.erika.gymtrack.dto.*;
import it.erika.gymtrack.entities.*;
import it.erika.gymtrack.enumes.Status;
import it.erika.gymtrack.enumes.Type;
import it.erika.gymtrack.exceptions.*;
import it.erika.gymtrack.filters.AccessFilter;
import it.erika.gymtrack.filters.CourseScheduleFilter;
import it.erika.gymtrack.filters.SubscriptionFilter;
import it.erika.gymtrack.mappers.AccessMapper;
import it.erika.gymtrack.mappers.ReferenceMapper;
import it.erika.gymtrack.repository.AccessRepository;
import it.erika.gymtrack.specifications.AccessSpecification;
import java.time.*;
import java.util.*;

import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Log4j2
@Service
public class AccessServiceImpl implements AccessService {

    private final AccessRepository repository;
    private final AccessMapper mapper;
    private final CertificateService certificateService;
    private final SubscriptionService subscriptionService;
    private final SuspensionService suspensionService;
    private final GymScheduleProperties gymProperties;
    private final ReferenceMapper referenceMapper;
    private final PaymentService paymentService;
    private final CourseScheduleService courseScheduleService;

    public AccessServiceImpl(
            AccessRepository repository,
            AccessMapper mapper,
            CertificateService certificateService,
            SubscriptionService subscriptionService,
            SuspensionService suspensionService,
            GymScheduleProperties gymProperties,
            ReferenceMapper referenceMapper,
            PaymentService paymentService, CourseScheduleService courseScheduleService) {
        this.repository = repository;
        this.mapper = mapper;
        this.certificateService = certificateService;
        this.subscriptionService = subscriptionService;
        this.suspensionService = suspensionService;
        this.gymProperties = gymProperties;
        this.referenceMapper = referenceMapper;
        this.paymentService = paymentService;
        this.courseScheduleService = courseScheduleService;
    }

    @Override
    public AccessDto insertAccess(AccessDto dto) {
        Access entity = new Access();
        log.info("Insert access {}", dto);
        entity.setCustomer(referenceMapper.toCustomer(dto.getCustomer().getId()));
        var subscriptionDto = searchSubscription(dto.getCustomer().getId());

        checkGymOpen();

        checkValidSubscription(subscriptionDto);

        checkPayment(subscriptionDto.getId());

        suspensionService.checkActiveSuspensionAtInstant(
                subscriptionDto.getId(), Instant.now()); // controllo nella data corrente

        checkValidCertificate(dto.getCustomer().getId());

        checkIfMaxDailyAccessWasExceeded(dto.getCustomer().getId(), subscriptionDto.getSubscriptionType());

        checkCourseAssociated(subscriptionDto.getId(), dto.getCourse().getId());

        checkCourseSchedule(dto.getCourse().getId());

        entity = repository.save(entity);
        return mapper.toDto(entity);
    }

    private SubscriptionDto searchSubscription(UUID customerId) {
        SubscriptionFilter subscriptionFilter = new SubscriptionFilter();
        subscriptionFilter.setCustomerId(customerId);
        var page = subscriptionService.searchSubscription(Pageable.ofSize(1), subscriptionFilter);
        if (page.isEmpty()) {
            throw new SubscriptionNotFoundException(HttpStatus.BAD_REQUEST, "Subscription not found");
        }
        return page.stream().findFirst().get();
    }

    private void checkValidSubscription(SubscriptionDto subscriptionDto) {
        var today = Instant.now();
        var isValidSubscription = today.isAfter(subscriptionDto.getStartDate())
                || today.equals(subscriptionDto.getStartDate()) && today.isBefore(subscriptionDto.getEndDate());
        if (!isValidSubscription) {
            throw new SubscriptionNotValidException(HttpStatus.BAD_REQUEST, "Subscription not valid");
        }
    }

    private void checkPayment(UUID subscriptionId) {
        var payments = paymentService.getPayments(subscriptionId);
        /* PaymentDto subscriptionPaymentDone = null;
         for(PaymentDto paymentDto : payments) {
            if(paymentDto.getStatus().equals(Status.DONE) && paymentDto.getType().equals(Type.SUBSCRIPTION)) {
                subscriptionPaymentDone = paymentDto;
            }
        }
        if(subscriptionPaymentDone==null) {
            throw new PaymentNotDoneException("Subscription Payment not done");
        } */

        payments.stream()
                .filter(dto ->
                        dto.getStatus().equals(Status.DONE) && dto.getType().equals(Type.SUBSCRIPTION))
                .findAny()
                .orElseThrow(
                        () -> new PaymentNotDoneException(HttpStatus.BAD_REQUEST, "Subscription Payment not done"));
    }

    private void checkValidCertificate(UUID certificateId) {
        if (!certificateService.existValidCertificate(certificateId)) {
            throw new CertificateNotValidException(HttpStatus.BAD_REQUEST, "Certificate not valid");
        }
    }

    private void checkGymOpen() {
        var openTime = LocalDate.now().atTime(gymProperties.schedule().opening());
        var closeTime = LocalDate.now().atTime(gymProperties.schedule().closing());
        var now = LocalDateTime.now();
        var nowIsAfterOrEqualOpenTime = now.isAfter(openTime) || now.equals(openTime);
        var nowIsBeforeOrIsEqualCloseTime = now.isBefore(closeTime) || now.equals(closeTime);
        var isGymOpen = nowIsAfterOrEqualOpenTime && nowIsBeforeOrIsEqualCloseTime;
        if (!isGymOpen) {
            throw new GymClosedException(HttpStatus.BAD_REQUEST, "Gym closed, the access is impossible");
        }
    }

    private void checkIfMaxDailyAccessWasExceeded(UUID customerId, SubscriptionTypeDto subscriptionTypeDto) {
        if (subscriptionTypeDto.getMaxDailyAccesses() != null) {
            var startDay = LocalDate.now().atStartOfDay(ZoneId.systemDefault()).toInstant();
            var endDay = LocalDate.now()
                    .atStartOfDay(ZoneId.systemDefault())
                    .with(LocalTime.MAX)
                    .toInstant();
            AccessFilter filter = new AccessFilter();

            filter.setCustomerId(customerId);
            filter.setAccessDateFrom(startDay);
            filter.setAccessDateTo(endDay);

            var customerDailyAccessList = searchAccess(Pageable.ofSize(1), filter);

            if (customerDailyAccessList.getTotalElements() >= subscriptionTypeDto.getMaxDailyAccesses()) {
                throw new MaxDailyAccessExceededException(
                        HttpStatus.BAD_REQUEST, "Access not permitted, max daily access was exceeded");
            }
        }
    }

    private void checkCourseAssociated(UUID subscriptionId, UUID courseId) {
        log.info("Finding courses associated to subscriptionId {}", subscriptionId);
        boolean courseAssociatedFound = false;
        List<CourseDto> courses = subscriptionService.getCourses(subscriptionId);
        log.info("Checking if course with id {} is associated to subscription {}", courseId, subscriptionId);
        for(var courseDto : courses) {
            if(courseDto.getId().equals(courseId)) {
                courseAssociatedFound = true;
                log.info("Found course associated to subscription {}", subscriptionId);
            }
        }
        if(!courseAssociatedFound) {
            log.info("course {} is not associated to subscription {}", courseId, subscriptionId);
            throw new CourseNotFoundException(HttpStatus.BAD_REQUEST, "Course inserted not valid");
        }
    }

    private void checkCourseSchedule(UUID courseId) {
        log.info("Checking courseSchedule of course {}", courseId);
        var now = LocalDateTime.now();
        var today = DayOfWeek.from(now);
        var time = now.toLocalTime();
        boolean courseScheduleFound = false;
        var filter = new CourseScheduleFilter();
        filter.setCourseId(courseId);
        filter.setDay(today);
        var courseSchedules = courseScheduleService.searchCourseSchedule(Pageable.unpaged(), filter);
        log.info("Checking list to find a courseScheduleDto");
        for(CourseScheduleDto courseScheduleDto : courseSchedules) {
            var nowIsAfterOrEqualStartTime = time.equals(courseScheduleDto.getStartTime()) || time.isAfter(courseScheduleDto.getStartTime());
            var nowIsBeforeEndTime = time.isBefore(courseScheduleDto.getEndTime());
            if(nowIsAfterOrEqualStartTime || nowIsBeforeEndTime) {
                courseScheduleFound = true;
                log.info("Course {} is starting", courseScheduleDto.getCourse().getId());
            }
        }
        if(!courseScheduleFound) {
            throw new CourseScheduleNotFoundException(HttpStatus.BAD_REQUEST, "Course %s not started or already finished".formatted(courseId));
        }

    }

    @Override
    public AccessDto getAccess(UUID id) {
        Optional<Access> oEntity = repository.findById(id);
        if (oEntity.isEmpty()) {
            throw new AccessNotFoundException(HttpStatus.NOT_FOUND, "Access not found");
        }
        var entity = oEntity.get();
        return mapper.toDto(entity);
    }

    @Override
    public Page<AccessDto> searchAccess(Pageable pageable, AccessFilter filter) {
        return repository.findAll(new AccessSpecification(filter), pageable).map(access -> mapper.toDto(access));
    }

    @Override
    public void deleteAccess(UUID id) {
        repository.deleteById(id);
    }
}
