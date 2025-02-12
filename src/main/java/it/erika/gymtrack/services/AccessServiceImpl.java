package it.erika.gymtrack.services;

import it.erika.gymtrack.dto.AccessDto;
import it.erika.gymtrack.entities.Access;
import it.erika.gymtrack.entities.Customer;
import it.erika.gymtrack.entities.Subscription;
import it.erika.gymtrack.exceptions.*;
import it.erika.gymtrack.filters.AccessFilter;
import it.erika.gymtrack.filters.SubscriptionFilter;
import it.erika.gymtrack.mappers.AccessMapper;
import it.erika.gymtrack.mappers.CustomerMapper;
import it.erika.gymtrack.repository.AccessRepository;
import it.erika.gymtrack.specifications.AccessSpecification;
import jakarta.transaction.Transactional;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.*;
import java.util.Date;
import java.util.Optional;
import java.util.UUID;
@Log4j2
@Service
public class AccessServiceImpl implements AccessService {

    private final AccessRepository repository;
    private final AccessMapper mapper;
    private final CustomerMapper customerMapper;
    private final CustomerService customerService;
    private final CertificateService certificateService;
    private final SubscriptionService subscriptionService;



    public AccessServiceImpl(AccessRepository repository, AccessMapper mapper, CustomerMapper customerMapper, CustomerService customerService, CertificateService certificateService, SubscriptionService subscriptionService) {
        this.repository = repository;
        this.mapper = mapper;
        this.customerMapper = customerMapper;
        this.customerService = customerService;
        this.certificateService = certificateService;
        this.subscriptionService = subscriptionService;
    }

    @Override
    public AccessDto insertAccess(AccessDto dto) {
        Access entity = new Access();
        log.info("Insert access {}", dto);
        var customerDto = customerService.getCustomer(dto.getCustomer().getId());
        entity.setCustomer(customerMapper.toEntity(customerDto));

        checkGymOpen();
        checkValidSubscription(dto.getCustomer().getId());
        checkValidCertificate(dto.getCustomer().getId());
        checkIsAccessAlreadyDoneToday(dto.getCustomer().getId());

        entity = repository.save(entity);
        return mapper.toDto(entity);
    }

    private void checkValidSubscription(UUID customerId) {
        var today = Instant.now();
        SubscriptionFilter subscriptionFilter = new SubscriptionFilter();
        subscriptionFilter.setCustomerId(customerId);
        var page = subscriptionService.searchSubscription(Pageable.ofSize(1), subscriptionFilter);
        if(page.isEmpty()) {
            throw new SubscriptionNotFoundException("Subscription not found");
        }
        var result = page.stream().findFirst().get();
        var isValidSubscription = today.isAfter(result.getStartDate()) || today.equals(result.getStartDate()) && today.isBefore(result.getEndDate());
        if(!isValidSubscription) {
            throw new SubscriptionNotValidException("Subscription not valid");
        }
    }

    private void checkValidCertificate(UUID certificateId) {
        if(!certificateService.existValidCertificate(certificateId)) {
            throw new CertificateNotValidException("Certificate not valid");
        }
    }

    private void checkGymOpen() {
        var openTime = LocalDate.now().atTime(7,0);
        var closeTime = LocalDate.now().atTime(LocalTime.MAX);
        var now = LocalDateTime.now();
        var nowIsAfterOrEqualOpenTime = now.isAfter(openTime) || now.equals(openTime);
        var nowIsBeforeOrIsEqualCloseTime = now.isBefore(closeTime) || now.equals(closeTime);
        var isGymOpen = nowIsAfterOrEqualOpenTime && nowIsBeforeOrIsEqualCloseTime;
        if(!isGymOpen) {
            throw new GymClosedException("Gym closed, the access is impossible");
        }
    }

    private void checkIsAccessAlreadyDoneToday(UUID customerId) {
        var startDay = LocalDate.now().atStartOfDay(ZoneId.systemDefault()).toInstant();
        var endDay = LocalDate.now().atStartOfDay(ZoneId.systemDefault()).with(LocalTime.MAX).toInstant();
        AccessFilter filter = new AccessFilter();
        filter.setCustomerId(customerId);
        filter.setAccessDateFrom(startDay);
        filter.setAccessDateTo(endDay);
        var customerAccess = searchAccess(Pageable.ofSize(1), filter);
        if(!customerAccess.isEmpty()) {
            throw new AccessAlreadyDoneException("This customer has already done his access today");
        }
    }

    @Override
    public AccessDto getAccess(UUID id) {
        Optional<Access> oEntity = repository.findById(id);
        if(oEntity.isEmpty()) {
            throw new AccessNotFoundException("Access not found");
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
