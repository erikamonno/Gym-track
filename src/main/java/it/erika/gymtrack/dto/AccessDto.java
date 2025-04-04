package it.erika.gymtrack.dto;

import jakarta.validation.constraints.NotNull;
import java.time.Instant;
import java.util.UUID;
import lombok.Data;

@Data
public class AccessDto {

    private UUID id;

    private Instant accessDate;

    @NotNull private CustomerDto customer;
}
