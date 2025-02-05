package it.erika.gymtrack.controllers;

import it.erika.gymtrack.dto.CustomerDto;
import it.erika.gymtrack.filters.CustomerFilter;
import it.erika.gymtrack.services.CustomerService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("customer")
public class CustomerController {

    private final CustomerService service;

    public CustomerController(CustomerService service) {
        this.service = service;
    }

    @PostMapping
    public CustomerDto insertCustomer(@RequestBody @Valid CustomerDto dto) {
        return service.insertCustomer(dto);
    }

    @GetMapping("{id}")
    public CustomerDto getCustomer(@PathVariable(name = "id") UUID id) {
        return service.getCustomer(id);
    }

    @GetMapping
    public Page<CustomerDto> searchCustomer(@PageableDefault Pageable pageable, CustomerFilter filter) {
        return service.searchCustomer(pageable, filter);
    }

    @PutMapping("{id}")
    public void updateCustomer(@PathVariable(name = "id") UUID id, @RequestBody @Valid CustomerDto dto) {
        service.updateCustomer(id, dto);
    }

    @DeleteMapping("{id}")
    public void deleteCustomer(@PathVariable(name = "id") UUID id) {
        service.deleteCustomer(id);
    }
}
