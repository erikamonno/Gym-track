package it.erika.gymtrack.mappers;

import it.erika.gymtrack.dto.AccessDto;
import it.erika.gymtrack.dto.CustomerDto;
import it.erika.gymtrack.entities.Access;
import it.erika.gymtrack.entities.Customer;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface AccessMapper {

    AccessDto toDto(Access entity);
    Access toEntity(AccessDto dto);
}
