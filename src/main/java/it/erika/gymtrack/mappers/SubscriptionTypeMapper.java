package it.erika.gymtrack.mappers;

import it.erika.gymtrack.dto.SubscriptionTypeDto;
import it.erika.gymtrack.entities.SubscriptionType;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface SubscriptionTypeMapper {

    SubscriptionTypeDto toDto(SubscriptionType entity);
    SubscriptionType toEntity(SubscriptionTypeDto dto);
}
