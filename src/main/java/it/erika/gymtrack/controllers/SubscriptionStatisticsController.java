package it.erika.gymtrack.controllers;

import it.erika.gymtrack.dto.AccessNumberDto;
import it.erika.gymtrack.dto.ExpiringCertificateDto;
import it.erika.gymtrack.dto.SubscriptionStatisticsDto;
import it.erika.gymtrack.filters.SubscriptionStatisticsFilter;
import it.erika.gymtrack.services.SubscriptionStatisticsService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("statistics")
public class SubscriptionStatisticsController {

    private final SubscriptionStatisticsService service;

    public SubscriptionStatisticsController(SubscriptionStatisticsService service) {
        this.service = service;
    }

    @GetMapping
    private SubscriptionStatisticsDto getSubscriptionStatistics() {
        return service.getSubscriptionStatistics();
    }

    @GetMapping
    private AccessNumberDto getNumberCheckIn(SubscriptionStatisticsFilter subscriptionStatisticsFilter) {
        return service.getNumberCheckIn(subscriptionStatisticsFilter);
    }

    @GetMapping
    private List<ExpiringCertificateDto> getMedicalCertificateExpiring() {
        return service.getMedicalCertificateExpiring();
    }
}
