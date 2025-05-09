package it.erika.gymtrack.entities;

import it.erika.gymtrack.enumes.Reason;
import jakarta.persistence.*;
import java.time.Instant;
import java.util.UUID;
import lombok.Data;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;
import org.hibernate.annotations.SoftDelete;

@Data
@Entity
@Table(name = "suspension")
@SoftDelete
public class Suspension {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id")
    private UUID id;

    @Column(name = "start_date")
    private Instant startDate;

    @Column(name = "end_date")
    private Instant endDate;

    @Enumerated(EnumType.STRING)
    @Column(name = "reason")
    private Reason reason;

    @Column(name = "note")
    private String note;

    @ManyToOne(
            fetch = FetchType.LAZY,
            optional = false)// quando salvo una suspension ci deve essere per forza una subscription
    @NotFound(action = NotFoundAction.EXCEPTION)
    @JoinColumn(name = "subscription_id")
    private Subscription subscription;

    @Column(name = "refund_suspension")
    private Boolean refundSuspension;

    @Column(name = "original_subscription_end_date")
    private Instant originalSubscriptionEndDate;
}
