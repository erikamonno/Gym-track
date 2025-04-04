package it.erika.gymtrack.entities;

import jakarta.persistence.*;
import java.time.Instant;
import java.util.UUID;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;

@Entity
@Table(name = "access")
@Data
public class Access {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id")
    private UUID id;

    @Column(name = "access_date")
    @CreationTimestamp // genera il valore in fase di inserimento del record
    private Instant accessDate;

    @ManyToOne
    @JoinColumn(name = "customer_id")
    private Customer customer;
}
