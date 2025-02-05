package it.erika.gymtrack.mappers;

import it.erika.gymtrack.dto.CustomerDto;
import it.erika.gymtrack.entities.Customer;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CustomerMapper {

    CustomerDto toDto(Customer entity);
    Customer toEntity(CustomerDto dto);
}
