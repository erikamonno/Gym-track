package it.erika.gymtrack.controllers;

import it.erika.gymtrack.dto.AccessNumberDto;
import it.erika.gymtrack.dto.ExpiringCertificateDto;
import it.erika.gymtrack.dto.SubscriptionStatisticsDto;
import it.erika.gymtrack.filters.SubscriptionStatisticsFilter;
import it.erika.gymtrack.services.SubscriptionStatisticsService;
import java.util.List;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("statistics")
public class SubscriptionStatisticsController {

    private final SubscriptionStatisticsService service;

    public SubscriptionStatisticsController(SubscriptionStatisticsService service) {
        this.service = service;
    }

    @GetMapping(path = "subscription")
    private SubscriptionStatisticsDto getSubscriptionStatistics() {
        return service.getSubscriptionStatistics();
    }

    @GetMapping(path = "checkIn")
    private AccessNumberDto getNumberCheckIn(SubscriptionStatisticsFilter subscriptionStatisticsFilter) {
        return service.getNumberCheckIn(subscriptionStatisticsFilter);
    }

    @GetMapping(path = "medicalCertificate/expiring")
    private List<ExpiringCertificateDto> getMedicalCertificateExpiring() {
        return service.getMedicalCertificateExpiring();
    }
}
