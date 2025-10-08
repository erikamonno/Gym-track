package it.erika.gymtrack.mappers;

import it.erika.gymtrack.entities.*;
import it.erika.gymtrack.repository.*;
import java.util.UUID;
import org.springframework.stereotype.Component;

@Component
public class ReferenceMapper {

    private final SubscriptionRepository subscriptionRepository;
    private final CustomerRepository customerRepository;
    private final SubscriptionTypeRepository subscriptionTypeRepository;
    private final PromotionRepository promotionRepository;
    private final CourseRepository courseRepository;

    public ReferenceMapper(
            SubscriptionRepository subscriptionRepository,
            CustomerRepository customerRepository,
            SubscriptionTypeRepository subscriptionTypeRepository,
            PromotionRepository promotionRepository,
            CourseRepository courseRepository) {
        this.subscriptionRepository = subscriptionRepository;
        this.customerRepository = customerRepository;
        this.subscriptionTypeRepository = subscriptionTypeRepository;
        this.promotionRepository = promotionRepository;
        this.courseRepository = courseRepository;
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

    public Promotion toPromotion(UUID id) {
        return promotionRepository.getReferenceById(id);
    }

    public Course toCourse(UUID id) {
        return courseRepository.getReferenceById(id);
    }
}
