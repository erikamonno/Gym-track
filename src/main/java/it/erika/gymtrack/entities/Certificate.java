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
    @Column(name = "id")
    private UUID id;

    @Column(name = "expiry_date")
    private Instant expiryDate;

    @Column(name = "content")
    private byte[] content;
}
