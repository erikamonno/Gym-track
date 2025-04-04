package it.erika.gymtrack.mappers;

import it.erika.gymtrack.entities.Customer;
import it.erika.gymtrack.entities.Subscription;
import it.erika.gymtrack.entities.SubscriptionType;
import it.erika.gymtrack.repository.CustomerRepository;
import it.erika.gymtrack.repository.SubscriptionRepository;
import java.util.UUID;

import it.erika.gymtrack.repository.SubscriptionTypeRepository;
import org.springframework.stereotype.Component;

@Component
public class ReferenceMapper {

    private final SubscriptionRepository subscriptionRepository;
    private final CustomerRepository customerRepository;
    private final SubscriptionTypeRepository subscriptionTypeRepository;

    public ReferenceMapper(SubscriptionRepository subscriptionRepository, CustomerRepository customerRepository, SubscriptionTypeRepository subscriptionTypeRepository) {
        this.subscriptionRepository = subscriptionRepository;
        this.customerRepository = customerRepository;
        this.subscriptionTypeRepository = subscriptionTypeRepository;
    }

    public Subscription toSubscription(UUID id) {
        return subscriptionRepository.getReferenceById(id);
    }

    public Customer toCustomer(UUID id) {
        return customerRepository.getReferenceById(id);
    }

    public SubscriptionType toSubscriptionType(UUID id) {
        return subscriptionTypeRepository.getReferenceById(id);
    }
}
