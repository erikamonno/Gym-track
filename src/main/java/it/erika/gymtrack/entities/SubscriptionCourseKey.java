package it.erika.gymtrack.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import java.io.Serializable;
import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@Embeddable
public class SubscriptionCourseKey implements Serializable {

    @Column(name = "course_id")
    private UUID courseId;

    @Column(name = "subscription_id")
    private UUID subscriptionId;
}
