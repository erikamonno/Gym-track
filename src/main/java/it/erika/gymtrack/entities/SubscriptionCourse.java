package it.erika.gymtrack.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "subscription_course")
@Getter
@Setter
@NoArgsConstructor
public class SubscriptionCourse {

    @EmbeddedId
    private SubscriptionCourseKey id;

    @ManyToOne
    @MapsId("subscriptionId")
    @JoinColumn(name = "subscription_id")
    private Subscription subscription;

    @ManyToOne
    @MapsId("courseId")
    @JoinColumn(name = "course_id")
    private Course course;
}
