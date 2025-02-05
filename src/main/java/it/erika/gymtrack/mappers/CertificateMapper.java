package it.erika.gymtrack.mappers;

import it.erika.gymtrack.dto.CertificateDto;
import it.erika.gymtrack.dto.CustomerDto;
import it.erika.gymtrack.entities.Certificate;
import it.erika.gymtrack.entities.Customer;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CertificateMapper {

    CertificateDto toDto(Certificate entity);
    Certificate toEntity(CertificateDto dto);
}
