package it.erika.gymtrack.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import java.time.LocalDate;
import java.util.UUID;
import lombok.Data;

@Data
public class CustomerDto {

    private UUID id;

    @NotBlank
    private String name;

    @NotBlank
    private String surname;

    @Email
    private String email;

    private String phone;

    private LocalDate birthDate;
}
