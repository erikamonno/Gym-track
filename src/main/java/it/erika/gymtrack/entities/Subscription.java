package it.erika.gymtrack.entities;

import jakarta.persistence.*;
import java.time.Instant;
import java.util.List;
import java.util.UUID;
import lombok.Data;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;
import org.hibernate.annotations.SoftDelete;

@Entity
@Table(name = "subscription")
@Data
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

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "subscription", cascade = CascadeType.REMOVE) //l'annotation softDeleted non va messa sulle OneToMany
    private List<Suspension> suspensions;
}
