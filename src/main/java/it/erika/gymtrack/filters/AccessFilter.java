package it.erika.gymtrack.filters;

import it.erika.gymtrack.dto.CustomerDto;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.Instant;
import java.util.UUID;

@Data
public class AccessFilter {

    private Instant accessDateFrom;

    private Instant accessDateTo;

    private UUID customerId;


}
