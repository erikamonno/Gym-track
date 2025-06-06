package it.erika.gymtrack.services;

import it.erika.gymtrack.dto.CustomerDto;
import it.erika.gymtrack.entities.Customer;
import it.erika.gymtrack.exceptions.CustomerNotFoundException;
import it.erika.gymtrack.filters.CustomerFilter;
import it.erika.gymtrack.mappers.CustomerMapper;
import it.erika.gymtrack.repository.CustomerRepository;
import it.erika.gymtrack.specifications.CustomerSpecification;
import jakarta.transaction.Transactional;
import java.util.Optional;
import java.util.UUID;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Log4j2
@Service
public class CustomerServiceImpl implements CustomerService {

    private final CustomerRepository repository;
    private final CustomerMapper mapper;

    public CustomerServiceImpl(CustomerRepository repository, CustomerMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    @Override
    public CustomerDto insertCustomer(CustomerDto dto) {
        Customer entity = new Customer();
        log.info("Insert customer {}", dto);
        entity.setName(dto.getName());
        entity.setSurname(dto.getSurname());
        entity.setEmail(dto.getEmail());
        entity.setPhone(dto.getPhone());
        entity.setBirthDate(dto.getBirthDate());
        entity = repository.save(entity);
        return mapper.toDto(entity);
    }

    @Override
    public CustomerDto getCustomer(UUID id) {
        Optional<Customer> oEntity = repository.findById(id);
        if (oEntity.isEmpty()) {
            throw new CustomerNotFoundException(HttpStatus.NOT_FOUND, "Customer not found");
        }
        var entity = oEntity.get();
        return mapper.toDto(entity);
    }

    @Override
    public Page<CustomerDto> searchCustomer(Pageable pageable, CustomerFilter filter) {
        return repository.findAll(new CustomerSpecification(filter), pageable).map(customer -> mapper.toDto(customer));
    }

    @Override
    @Transactional
    public void updateCustomer(UUID id, CustomerDto dto) {
        Optional<Customer> oEntity = repository.findById(id);
        if (oEntity.isEmpty()) {
            throw new CustomerNotFoundException(HttpStatus.NOT_FOUND, "Customer not found");
        }
        var entity = oEntity.get();
        entity.setName(dto.getName());
        entity.setSurname(dto.getSurname());
        entity.setEmail(dto.getEmail());
        entity.setPhone(dto.getPhone());
        entity.setBirthDate(dto.getBirthDate());
    }

    @Override
    public void deleteCustomer(UUID id) {
        repository.deleteById(id);
    }
}
