package it.erika.gymtrack.entities;

import jakarta.persistence.*;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;
import lombok.Data;
import org.hibernate.annotations.SoftDelete;

@Entity
@Table(name = "customer")
@Data
@SoftDelete // ci permette di tenere traccia dei record che vengono eliminati con la delete, quindi con una
// cancellazione logica che avviene attraverso un boolean che indica se il record è stato cancellato o no
public class Customer {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id")
    private UUID id;

    @Column(name = "name")
    private String name;

    @Column(name = "surname")
    private String surname;

    @Column(name = "email")
    private String email;

    @Column(name = "phone")
    private String phone;

    @Column(name = "birth_date")
    private LocalDate birthDate;

    // con il lazy, hibernate fa la query per recuperare le entity  solo quando chiamo la get e non a prescindere
    // con il mappedBy vado a specificare l'attributo dell'entity figlia(subscription) che mappa la relazione con questa
    // entity
    // l'annotation softDeleted non va messa sulle OneToMany
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "customer", cascade = CascadeType.REMOVE)
    private List<Subscription> subscriptions;
}
