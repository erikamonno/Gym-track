package it.erika.gymtrack.services;

import it.erika.gymtrack.dto.AccessNumberDto;
import it.erika.gymtrack.dto.ExpiringCertificateDto;
import it.erika.gymtrack.dto.InvoiceStatisticsDto;
import it.erika.gymtrack.dto.SubscriptionStatisticsDto;
import it.erika.gymtrack.filters.InvoiceStatisticsFilter;
import it.erika.gymtrack.filters.SubscriptionStatisticsFilter;
import java.util.List;

public interface SubscriptionStatisticsService {

    SubscriptionStatisticsDto getSubscriptionStatistics();

    AccessNumberDto getNumberCheckIn(SubscriptionStatisticsFilter subscriptionStatisticsFilter);

    List<ExpiringCertificateDto> getMedicalCertificateExpiring();

    InvoiceStatisticsDto getStatisticsInvoices(InvoiceStatisticsFilter filter);
}
