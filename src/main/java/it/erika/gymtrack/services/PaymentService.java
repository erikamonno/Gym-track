package it.erika.gymtrack.services;

import it.erika.gymtrack.dto.PaymentDto;
import java.util.List;
import java.util.UUID;

public interface PaymentService {

    PaymentDto insertPayment(PaymentDto dto);

    List<PaymentDto> getPayments(UUID subscriptionId);

    void pay(UUID id);
}
