package it.erika.gymtrack.services;

import it.erika.gymtrack.dto.CustomerDto;
import it.erika.gymtrack.entities.Customer;
import it.erika.gymtrack.filters.CustomerFilter;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.UUID;

public interface CustomerService {

    CustomerDto insertCustomer(CustomerDto dto);
    CustomerDto getCustomer(UUID id);
    Page<CustomerDto> searchCustomer(Pageable pageable, CustomerFilter filter);
    void updateCustomer(UUID id, CustomerDto dto);
    void deleteCustomer(UUID id);
}
