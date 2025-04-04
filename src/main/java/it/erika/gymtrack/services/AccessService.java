package it.erika.gymtrack.services;

import it.erika.gymtrack.dto.AccessDto;
import it.erika.gymtrack.filters.AccessFilter;
import java.util.UUID;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface AccessService {

    AccessDto insertAccess(AccessDto dto);

    AccessDto getAccess(UUID id);

    Page<AccessDto> searchAccess(Pageable pageable, AccessFilter filter);

    void deleteAccess(UUID id);
}
