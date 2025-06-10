package it.erika.gymtrack.mappers;

import it.erika.gymtrack.dto.PaymentDto;
import it.erika.gymtrack.entities.Payment;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface PaymentMapper {

    PaymentDto toDto(Payment entity);
}
