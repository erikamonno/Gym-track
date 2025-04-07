package it.erika.gymtrack.services;

import it.erika.gymtrack.configurations.GymStatisticsProperties;
import it.erika.gymtrack.dto.AccessNumberDto;
import it.erika.gymtrack.dto.ExpiringCertificateDto;
import it.erika.gymtrack.dto.SubscriptionStatisticsDto;
import it.erika.gymtrack.entities.Certificate;
import it.erika.gymtrack.filters.SubscriptionStatisticsFilter;
import it.erika.gymtrack.repository.AccessRepository;
import it.erika.gymtrack.repository.CertificateRepository;
import it.erika.gymtrack.repository.SubscriptionRepository;
import it.erika.gymtrack.specifications.*;
import java.time.Duration;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class SubscriptionStatisticsServiceImpl implements SubscriptionStatisticsService {

    private final SubscriptionRepository subscriptionRepository;
    private final AccessRepository accessRepository;
    private final CertificateRepository certificateRepository;
    private final CustomerService customerService;
    private final GymStatisticsProperties gymStatisticsProperties;

    public SubscriptionStatisticsServiceImpl(
            SubscriptionRepository subscriptionRepository,
            AccessRepository accessRepository,
            CertificateRepository certificateRepository,
            CustomerService customerService, GymStatisticsProperties gymStatisticsProperties) {
        this.subscriptionRepository = subscriptionRepository;
        this.accessRepository = accessRepository;
        this.certificateRepository = certificateRepository;
        this.customerService = customerService;
        this.gymStatisticsProperties = gymStatisticsProperties;
    }

    @Override
    public SubscriptionStatisticsDto getSubscriptionStatistics() {
        var activeSubscriptionNumber = subscriptionRepository.count(new ActiveSubscriptionSpecification());
        var expiredSubscriptionNumber = subscriptionRepository.count(new ExpiredSubscriptionSpecification());
        var duration = gymStatisticsProperties.subscription().expiringSoon();
        var expiringSoonSubscription =
                subscriptionRepository.count(new ExpiringSoonSubscriptionSpecification(duration));
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
        var duration = gymStatisticsProperties.certificates().expiringSoon();
        return certificateRepository.findAll(new ExpiringCertificateSpecification(duration)).stream()
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
