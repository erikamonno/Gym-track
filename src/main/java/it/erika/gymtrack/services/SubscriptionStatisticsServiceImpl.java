package it.erika.gymtrack.services;

import it.erika.gymtrack.configurations.GymStatisticsProperties;
import it.erika.gymtrack.dto.AccessNumberDto;
import it.erika.gymtrack.dto.ExpiringCertificateDto;
import it.erika.gymtrack.dto.InvoiceStatisticsDto;
import it.erika.gymtrack.dto.SubscriptionStatisticsDto;
import it.erika.gymtrack.entities.Certificate;
import it.erika.gymtrack.entities.Payment;
import it.erika.gymtrack.enumes.Status;
import it.erika.gymtrack.filters.InvoiceStatisticsFilter;
import it.erika.gymtrack.filters.SubscriptionStatisticsFilter;
import it.erika.gymtrack.repository.AccessRepository;
import it.erika.gymtrack.repository.CertificateRepository;
import it.erika.gymtrack.repository.PaymentRepository;
import it.erika.gymtrack.repository.SubscriptionRepository;
import it.erika.gymtrack.specifications.*;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;

import lombok.extern.log4j.Log4j2;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

@Service
@Log4j2
public class SubscriptionStatisticsServiceImpl implements SubscriptionStatisticsService {

    private final SubscriptionRepository subscriptionRepository;
    private final AccessRepository accessRepository;
    private final CertificateRepository certificateRepository;
    private final CustomerService customerService;
    private final GymStatisticsProperties gymStatisticsProperties;
    private final PaymentRepository paymentRepository;


    public SubscriptionStatisticsServiceImpl(
            SubscriptionRepository subscriptionRepository,
            AccessRepository accessRepository,
            CertificateRepository certificateRepository,
            CustomerService customerService,
            GymStatisticsProperties gymStatisticsProperties, PaymentRepository paymentRepository) {
        this.subscriptionRepository = subscriptionRepository;
        this.accessRepository = accessRepository;
        this.certificateRepository = certificateRepository;
        this.customerService = customerService;
        this.gymStatisticsProperties = gymStatisticsProperties;
        this.paymentRepository = paymentRepository;

    }

    @Override
    public SubscriptionStatisticsDto getSubscriptionStatistics() {
        var activeSubscriptionNumber = subscriptionRepository.count(new ActiveSubscriptionSpecification());
        var expiredSubscriptionNumber = subscriptionRepository.count(new ExpiredSubscriptionSpecification());
        var duration = gymStatisticsProperties.subscriptions().expiringSoon();
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

    @Override
    public InvoiceStatisticsDto getStatisticsInvoices(InvoiceStatisticsFilter filter) {
        log.info("Getting invoices with filter {}", filter);
        InvoiceStatisticsDto dto = new InvoiceStatisticsDto();
        var payments = paymentRepository.findAll(new InvoiceStatisticsSpecification(filter));
        var amount = 0d;
        for(Payment payment : payments) {
            amount += payment.getAmount();
        }
        var paymentsDone = payments.size();
        dto.setTotalInvoices((long) paymentsDone);
        dto.setTotalAmount(amount);
        return dto;
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
