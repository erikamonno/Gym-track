package it.erika.gymtrack.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.time.Instant;
import java.util.UUID;
import lombok.Data;

@Data
public class CourseDto {

    private UUID id;

    @NotBlank
    private String name;

    @NotNull private Instant validFrom;

    @NotNull private Instant validTo;
}
