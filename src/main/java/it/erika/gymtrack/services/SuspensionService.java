package it.erika.gymtrack.services;

import it.erika.gymtrack.dto.SuspensionDto;
import it.erika.gymtrack.filters.SuspensionFilter;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.UUID;

public interface SuspensionService {

    SuspensionDto insertSuspension(SuspensionDto dto);
    SuspensionDto getSuspension(UUID id);
    Page<SuspensionDto> searchSuspension(Pageable pageable, SuspensionFilter filter);
    void updateSuspension(SuspensionDto dto, UUID id);
    void deleteSuspension(UUID id);
}
