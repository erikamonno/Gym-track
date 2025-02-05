package it.erika.gymtrack.dto;

import it.erika.gymtrack.entities.Customer;
import jakarta.persistence.Column;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;

import java.time.Instant;
import java.util.UUID;

@Data
public class AccessDto {

    private UUID id;

    @NotBlank
    private Instant accessDate;

    @NotNull
    private CustomerDto customer;
}
