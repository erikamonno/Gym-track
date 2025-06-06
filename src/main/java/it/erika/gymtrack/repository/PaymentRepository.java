package it.erika.gymtrack.repository;

import it.erika.gymtrack.entities.Payment;
import java.util.List;
import java.util.UUID;

import it.erika.gymtrack.enumes.Status;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface PaymentRepository extends JpaRepository<Payment, UUID>, JpaSpecificationExecutor<Payment> {

    List<Payment> findBySubscription_Id(UUID subscriptionId);
    Integer countByStatus(Status status);
    List<Payment> findByStatus(Status status);
}
