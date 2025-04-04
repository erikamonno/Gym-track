package it.erika.gymtrack.mappers;

import it.erika.gymtrack.dto.AccessDto;
import it.erika.gymtrack.entities.Access;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface AccessMapper {

    AccessDto toDto(Access entity);

    Access toEntity(AccessDto dto);
}
