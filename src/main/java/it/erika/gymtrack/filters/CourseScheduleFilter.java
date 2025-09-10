package it.erika.gymtrack.filters;

import it.erika.gymtrack.entities.Course;

import lombok.Data;


import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.UUID;

@Data
public class CourseScheduleFilter {

    private DayOfWeek day;

    private UUID courseId;
}
