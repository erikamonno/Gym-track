package it.erika.gymtrack.entities;

import jakarta.persistence.*;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;
import lombok.Data;
import org.hibernate.annotations.SoftDelete;

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

    @Column(name = "currency")
    private String currency;

    @Column(name = "amount")
    private Double amount;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "subscriptionType", cascade = CascadeType.REMOVE)
    private Set<Promotion> promotions = new HashSet<>();
}
