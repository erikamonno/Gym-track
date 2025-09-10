package it.erika.gymtrack.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.UUID;

@Data
public class CourseScheduleDto {

    private UUID id;

    @NotNull
    private DayOfWeek day;

    @NotNull
    private LocalTime startTime;

    @NotNull
    private LocalTime endTime;

    private CourseDto course;
}
