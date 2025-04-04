package it.erika.gymtrack.filters;

import java.time.LocalDate;
import lombok.Data;

@Data
public class CustomerFilter {

    private String name;

    private String surname;

    private String email;

    private String phone;

    private LocalDate birthDateTo;

    private LocalDate birthDateFrom;
}
