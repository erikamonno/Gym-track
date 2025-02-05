package it.erika.gymtrack.mappers;

import it.erika.gymtrack.dto.CustomerDto;
import it.erika.gymtrack.dto.SubscriptionDto;
import it.erika.gymtrack.entities.Customer;
import it.erika.gymtrack.entities.Subscription;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface SubscriptionMapper {

    SubscriptionDto toDto(Subscription entity);
    Subscription toEntity(SubscriptionDto dto);
}
