package it.erika.gymtrack.services;

import it.erika.gymtrack.dto.AccessNumberDto;
import it.erika.gymtrack.dto.CertificateDto;
import it.erika.gymtrack.dto.ExpiringCertificateDto;
import it.erika.gymtrack.dto.SubscriptionStatisticsDto;
import it.erika.gymtrack.entities.Certificate;
import it.erika.gymtrack.filters.SubscriptionStatisticsFilter;
import it.erika.gymtrack.repository.AccessRepository;
import it.erika.gymtrack.repository.CertificateRepository;
import it.erika.gymtrack.repository.CustomerRepository;
import it.erika.gymtrack.repository.SubscriptionRepository;
import it.erika.gymtrack.specifications.*;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;


import java.time.Duration;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;

@Service
public class SubscriptionStatisticsServiceImpl implements SubscriptionStatisticsService {

    private final SubscriptionRepository subscriptionRepository;
    private final AccessRepository accessRepository;
    private final CertificateRepository certificateRepository;
    private final CustomerService customerService;

    public SubscriptionStatisticsServiceImpl(SubscriptionRepository subscriptionRepository, AccessRepository accessRepository, CertificateRepository certificateRepository, CustomerService customerService) {
        this.subscriptionRepository = subscriptionRepository;
        this.accessRepository = accessRepository;
        this.certificateRepository = certificateRepository;
        this.customerService = customerService;
    }

    @Override
    public SubscriptionStatisticsDto getSubscriptionStatistics() {
        var activeSubscriptionNumber = subscriptionRepository.count(new ActiveSubscriptionSpecification());
        var expiredSubscriptionNumber = subscriptionRepository.count(new ExpiredSubscriptionSpecification());
        var duration = Duration.ofDays(30);
        var expiringSoonSubscription = subscriptionRepository.count(new ExpiringSoonSubscriptionSpecification(duration));
        SubscriptionStatisticsDto dto = new SubscriptionStatisticsDto();
        dto.setActive(activeSubscriptionNumber);
        dto.setExpired(expiredSubscriptionNumber);
        dto.setExpiringSoon(expiringSoonSubscription);
        return dto;
    }

    @Override
    public AccessNumberDto getNumberCheckIn(SubscriptionStatisticsFilter subscriptionStatisticsFilter) {
        var numberCheckIn = accessRepository.count(new NumberCheckInSpecification(subscriptionStatisticsFilter));
        AccessNumberDto accessNumberDto = new AccessNumberDto();
        accessNumberDto.setAccessNumber(numberCheckIn);
        return accessNumberDto;
    }

    @Override
    public List<ExpiringCertificateDto> getMedicalCertificateExpiring() {
        var duration = Duration.ofDays(30);
        return certificateRepository.findAll(new ExpiringCertificateSpecification(duration))
                .stream()
                .map(certificate -> toDto(certificate))
                .toList();

    }

    private ExpiringCertificateDto toDto(Certificate certificate) {
        ExpiringCertificateDto dto = new ExpiringCertificateDto();
        var customer = customerService.getCustomer(certificate.getId());
        dto.setClientId(certificate.getId());
        dto.setExpiresOn(LocalDate.ofInstant(certificate.getExpiryDate(), ZoneId.systemDefault()));
        dto.setName(customer.getName());
        return dto;
    }
}
