package it.erika.gymtrack.entities;

import jakarta.persistence.*;
import java.time.Instant;
import java.util.UUID;
import lombok.Data;

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
