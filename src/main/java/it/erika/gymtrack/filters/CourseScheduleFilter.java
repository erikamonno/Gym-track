package it.erika.gymtrack.filters;

import java.time.DayOfWeek;
import java.util.UUID;
import lombok.Data;

@Data
public class CourseScheduleFilter {

    private DayOfWeek day;

    private UUID courseId;
}
