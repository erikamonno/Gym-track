package it.erika.gymtrack.services;

import it.erika.gymtrack.configurations.GymScheduleProperties;
import it.erika.gymtrack.dto.AccessDto;
import it.erika.gymtrack.dto.SubscriptionDto;
import it.erika.gymtrack.dto.SubscriptionTypeDto;
import it.erika.gymtrack.entities.*;
import it.erika.gymtrack.enumes.Status;
import it.erika.gymtrack.enumes.Type;
import it.erika.gymtrack.exceptions.*;
import it.erika.gymtrack.filters.AccessFilter;
import it.erika.gymtrack.filters.SubscriptionFilter;
import it.erika.gymtrack.mappers.AccessMapper;
import it.erika.gymtrack.mappers.ReferenceMapper;
import it.erika.gymtrack.repository.AccessRepository;
import it.erika.gymtrack.specifications.AccessSpecification;
import java.time.*;
import java.util.Optional;
import java.util.UUID;
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

    public AccessServiceImpl(
            AccessRepository repository,
            AccessMapper mapper,
            CertificateService certificateService,
            SubscriptionService subscriptionService,
            SuspensionService suspensionService,
            GymScheduleProperties gymProperties,
            ReferenceMapper referenceMapper,
            PaymentService paymentService) {
        this.repository = repository;
        this.mapper = mapper;
        this.certificateService = certificateService;
        this.subscriptionService = subscriptionService;
        this.suspensionService = suspensionService;
        this.gymProperties = gymProperties;
        this.referenceMapper = referenceMapper;
        this.paymentService = paymentService;
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
