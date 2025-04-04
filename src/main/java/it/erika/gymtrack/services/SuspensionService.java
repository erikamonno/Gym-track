package it.erika.gymtrack.services;

import it.erika.gymtrack.dto.SuspensionDto;
import it.erika.gymtrack.filters.SuspensionFilter;
import java.time.Instant;
import java.util.UUID;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface SuspensionService {

    SuspensionDto insertSuspension(SuspensionDto dto);

    void checkActiveSuspensionAtInstant(UUID subscriptionId, Instant atDate);

    SuspensionDto getSuspension(UUID id);

    Page<SuspensionDto> searchSuspension(Pageable pageable, SuspensionFilter filter);

    void updateSuspension(SuspensionDto dto, UUID id);

    void deleteSuspension(UUID id);
}
