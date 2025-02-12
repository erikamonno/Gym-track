package it.erika.gymtrack.services;

import it.erika.gymtrack.dto.AccessDto;
import it.erika.gymtrack.entities.Access;
import it.erika.gymtrack.filters.AccessFilter;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.UUID;

public interface AccessService {

    AccessDto insertAccess(AccessDto dto);
    AccessDto getAccess(UUID id);
    Page<AccessDto> searchAccess(Pageable pageable, AccessFilter filter);
    void deleteAccess(UUID id);
}
