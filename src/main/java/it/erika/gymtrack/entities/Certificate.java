package it.erika.gymtrack.entities;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;

import java.time.Instant;
import java.util.UUID;

@Entity
@Table(name = "certificate")
@Data

public class Certificate {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id")
    private UUID id;

    @Column(name = "expiry_date")
    @CreationTimestamp
    private Instant expiryDate;

    @Column(name = "content")
    private byte[] content;
}
