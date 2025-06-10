package it.erika.gymtrack.entities;

import jakarta.persistence.*;
import java.time.Instant;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;
import org.hibernate.annotations.SoftDelete;

@Entity
@Table(name = "subscription")
@Getter
@Setter
@NoArgsConstructor
@SoftDelete
public class Subscription {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id")
    private UUID id;

    @Column(name = "start_date")
    private Instant startDate;

    @Column(name = "end_date")
    private Instant endDate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "subscription_type_id")
    private SubscriptionType subscriptionType;

    @ManyToOne(fetch = FetchType.LAZY)
    @NotFound(action = NotFoundAction.EXCEPTION)
    @JoinColumn(name = "customer_id")
    private Customer customer;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "promotion_id")
    private Promotion promotion;

    @OneToMany(
            fetch = FetchType.LAZY,
            mappedBy = "subscription",
            cascade = CascadeType.REMOVE) // l'annotation softDeleted non va messa sulle OneToMany
    private Set<Suspension> suspensions = new HashSet<>();

    @OneToMany(
            fetch = FetchType.LAZY,
            mappedBy = "subscription",
            cascade =
                    CascadeType
                            .ALL) // il mappedBy è il nome dell'attributo su cui e definita la relazione nella tabella
    // payment
    private Set<Payment> payments = new HashSet<>();

    public Subscription addPayment(Payment payment) {
        payment.setSubscription(this);
        getPayments().add(payment);
        return this;
    }
}
