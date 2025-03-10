package it.erika.gymtrack.mappers;

import it.erika.gymtrack.dto.SuspensionDto;
import it.erika.gymtrack.entities.Suspension;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface SuspensionMapper {

    SuspensionDto toDto(Suspension entity);
    Suspension toEntity(SuspensionDto dto);
}
