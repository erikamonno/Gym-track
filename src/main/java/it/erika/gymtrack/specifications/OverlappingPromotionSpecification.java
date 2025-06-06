package it.erika.gymtrack.specifications;

import it.erika.gymtrack.entities.Promotion;
import it.erika.gymtrack.entities.Promotion_;
import it.erika.gymtrack.entities.SubscriptionType_;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import org.springframework.data.jpa.domain.Specification;

import java.time.Instant;
import java.util.UUID;

public class OverlappingPromotionSpecification implements Specification<Promotion> {

    private UUID id;

    private UUID subscriptionTypeId;

    private Instant validTo;

    private Instant validFrom;

    public OverlappingPromotionSpecification(UUID id, UUID subscriptionTypeId, Instant validTo, Instant validFrom) {
        this.id = id;
        this.subscriptionTypeId = subscriptionTypeId;
        this.validTo = validTo;
        this.validFrom = validFrom;
    }

    @Override
    public Predicate toPredicate(Root<Promotion> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
        return Specification.allOf(
                subscriptionTypeIdEqual(),
                validFromLessThenOrEqualTo().and(validToGreaterThenOrEqualTo()),
                promotionIdEqual()
        ).toPredicate(root, query, criteriaBuilder);
    }

    private Specification<Promotion> subscriptionTypeIdEqual() {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.equal(root.get(Promotion_.subscriptionType).get(SubscriptionType_.id), subscriptionTypeId);
    }

    private Specification<Promotion> validToGreaterThenOrEqualTo() {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.greaterThanOrEqualTo(root.get(Promotion_.validTo), validFrom);
    }

    private Specification<Promotion> validFromLessThenOrEqualTo() {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.lessThanOrEqualTo(root.get(Promotion_.validFrom), validTo);
    }

    //La clausola viene attivata solo nel caso dell'update perchè vado a controllare che il controllo delle date
    // non si verifichi sulla stessa data che sto aggiornando. Nel caso dell'insert in
    // cui non abbiamo un id da passare, questo risulterà null e avverrà il controllo
    // delle date rispetto a quelle delle promozioni eventualmente già presenti
    private Specification<Promotion> promotionIdEqual() {
        return (root, query, criteriaBuilder) -> {
            if(id==null) {
                return null;
            } else {
                return criteriaBuilder.notEqual(root.get(Promotion_.id), id);
            }
        };
    }
}
