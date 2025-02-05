package it.erika.gymtrack.entities;

import it.erika.gymtrack.enumes.SubscriptionType;
import jakarta.persistence.*;
import lombok.Data;

import java.time.Instant;
import java.util.UUID;

@Entity
@Table(name = "subscription")
@Data

public class Subscription {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id")
    private UUID id;

    @Column(name = "start_date")
    private Instant startDate;

    @Column(name = "end_date")
    private Instant endDate;

    @Column(name = "type")
    @Enumerated(EnumType.STRING)
    private SubscriptionType type;
}
