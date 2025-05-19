package it.erika.gymtrack.entities;

import jakarta.persistence.*;
import java.util.UUID;
import lombok.Data;

@Data
@Entity
@Table(name = "subscription_type")
public class SubscriptionType {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id")
    private UUID id;

    @Column(name = "name")
    private String name;

    @Column(name = "description")
    private String description;

    @Column(name = "duration_in_days")
    private Integer durationInDays;

    @Column(name = "max_daily_accesses")
    private Integer maxDailyAccesses;
}
