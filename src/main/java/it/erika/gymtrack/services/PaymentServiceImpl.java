package it.erika.gymtrack.services;

import it.erika.gymtrack.dto.PaymentDto;
import it.erika.gymtrack.enumes.Status;
import it.erika.gymtrack.exceptions.EntityNotFoundException;
import it.erika.gymtrack.exceptions.PaymentAlreadyDoneException;
import it.erika.gymtrack.mappers.PaymentMapper;
import it.erika.gymtrack.repository.PaymentRepository;
import jakarta.transaction.Transactional;
import java.time.Instant;
import java.util.List;
import java.util.UUID;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

@Service
@Log4j2
public class PaymentServiceImpl implements PaymentService {

    private final PaymentRepository paymentRepository;
    private final PaymentMapper paymentMapper;

    public PaymentServiceImpl(PaymentRepository paymentRepository, PaymentMapper paymentMapper) {
        this.paymentRepository = paymentRepository;
        this.paymentMapper = paymentMapper;
    }

    @Override
    public PaymentDto insertPayment(PaymentDto dto) {
        return null;
    }

    @Override
    public List<PaymentDto> getPayments(UUID subscriptionId) {
        var payments = paymentRepository.findBySubscription_Id(subscriptionId);
        return payments.stream().map(payment -> paymentMapper.toDto(payment)).toList();
    }

    @Override
    @Transactional
    public void pay(UUID id) {
        var oEntity = paymentRepository.findById(id);
        if (oEntity.isEmpty()) {
            throw new EntityNotFoundException("Entity not found");
        } else {
            var payment = oEntity.get();
            log.info("Payment found with id: {}", payment.getId());

            if (payment.getStatus().equals(Status.DONE)) {
                throw new PaymentAlreadyDoneException("Payment already done with that id");
            }
            payment.setStatus(Status.DONE);
            payment.setPaymentTimestamp(Instant.now());
        }
    }
}
