package it.erika.gymtrack.mappers;

import it.erika.gymtrack.dto.PromotionDto;
import it.erika.gymtrack.entities.Promotion;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface PromotionMapper {

    PromotionDto toDto(Promotion entity);
}
