package it.erika.gymtrack.filters;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import java.time.Instant;
import java.time.LocalDate;

@Data
public class CustomerFilter {

    private String name;

    private String surname;

    private String email;

    private String phone;

    private LocalDate birthDateTo;

    private LocalDate birthDateFrom;
}
