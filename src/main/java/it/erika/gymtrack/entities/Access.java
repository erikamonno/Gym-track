package it.erika.gymtrack.entities;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;

import java.time.Instant;
import java.util.UUID;

@Entity
@Table(name = "access")
@Data

public class Access {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id")
    private UUID id;

    @Column(name = "access_date")
    @CreationTimestamp
    private Instant accessDate;

    @ManyToOne
    @JoinColumn(name = "customer_id")
    private Customer customer;

}
