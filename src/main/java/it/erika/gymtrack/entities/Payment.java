package it.erika.gymtrack.entities;

import it.erika.gymtrack.enumes.Status;
import it.erika.gymtrack.enumes.Type;
import jakarta.persistence.*;
import java.time.Instant;
import java.util.UUID;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;
import org.hibernate.annotations.SoftDelete;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "payment")
@SoftDelete
public class Payment {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id")
    private UUID id;

    @Column(name = "payment_timestamp")
    private Instant paymentTimestamp;

    @Column(name = "type")
    @Enumerated(value = EnumType.STRING)
    private Type type;

    @Column(name = "status")
    @Enumerated(value = EnumType.STRING)
    private Status status;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @NotFound(action = NotFoundAction.EXCEPTION)
    @JoinColumn(name = "subscription_id")
    private Subscription subscription;
}
