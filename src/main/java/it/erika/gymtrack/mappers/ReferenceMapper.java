package it.erika.gymtrack.mappers;

import it.erika.gymtrack.entities.Customer;
import it.erika.gymtrack.entities.Subscription;
import it.erika.gymtrack.repository.CustomerRepository;
import it.erika.gymtrack.repository.SubscriptionRepository;
import java.util.UUID;
import org.springframework.stereotype.Component;

@Component
public class ReferenceMapper {

    private final SubscriptionRepository subscriptionRepository;
    private final CustomerRepository customerRepository;

    public ReferenceMapper(SubscriptionRepository subscriptionRepository, CustomerRepository customerRepository) {
        this.subscriptionRepository = subscriptionRepository;
        this.customerRepository = customerRepository;
    }

    public Subscription toSubscription(UUID id) {
        return subscriptionRepository.getReferenceById(id);
    }

    public Customer toCustomer(UUID id) {
        return customerRepository.getReferenceById(id);
    }
}
