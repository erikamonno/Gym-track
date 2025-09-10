package it.erika.gymtrack.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.Instant;
import java.util.UUID;

@Data
public class CourseDto {

    private UUID id;

    @NotBlank
    private String name;

    @NotNull
    private Instant validFrom;

    @NotNull
    private Instant validTo;
}
