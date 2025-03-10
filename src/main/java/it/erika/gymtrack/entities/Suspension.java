package it.erika.gymtrack.entities;

import it.erika.gymtrack.enumes.Reason;
import jakarta.persistence.*;
import lombok.Data;

import java.time.Instant;
import java.util.UUID;
@Data
@Entity
@Table(name = "suspension")
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

    @ManyToOne
    @JoinColumn(name = "subscription_id")
    private Subscription subscription;

}
