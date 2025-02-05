package it.erika.gymtrack.services;

import it.erika.gymtrack.dto.AccessDto;
import it.erika.gymtrack.entities.Access;
import it.erika.gymtrack.exceptions.AccessNotFoundException;
import it.erika.gymtrack.filters.AccessFilter;
import it.erika.gymtrack.mappers.AccessMapper;
import it.erika.gymtrack.mappers.CustomerMapper;
import it.erika.gymtrack.repository.AccessRepository;
import it.erika.gymtrack.specifications.AccessSpecification;
import jakarta.transaction.Transactional;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;
@Log4j2
@Service
public class AccessServiceImpl implements AccessService {

    private final AccessRepository repository;
    private final AccessMapper mapper;
    private final CustomerMapper customerMapper;
    private final CustomerService customerService;


    public AccessServiceImpl(AccessRepository repository, AccessMapper mapper, CustomerMapper customerMapper, CustomerService customerService) {
        this.repository = repository;
        this.mapper = mapper;
        this.customerMapper = customerMapper;
        this.customerService = customerService;
    }

    @Override
    public AccessDto insertAccess(AccessDto dto) {
        Access entity = new Access();
        log.info("Insert access {}", dto);
        entity.setId(dto.getId());
        entity.setAccessDate(dto.getAccessDate());
        var customerDto = customerService.getCustomer(dto.getCustomer().getId());
        entity.setCustomer(customerMapper.toEntity(customerDto));
        entity = repository.save(entity);
        return mapper.toDto(entity);
    }

    @Override
    public AccessDto getAccess(UUID id) {
        Optional<Access> oEntity = repository.findById(id);
        if(oEntity.isEmpty()) {
            throw new AccessNotFoundException("Access not found");
        }
        var entity = oEntity.get();
        return mapper.toDto(entity);
    }

    @Override
    public Page<AccessDto> searchAccess(Pageable pageable, AccessFilter filter) {
        return repository.findAll(new AccessSpecification(filter), pageable).map(access -> mapper.toDto(access));
    }

    @Override
    @Transactional
    public void updateAccess(UUID id, AccessDto dto) {
        Optional<Access> oEntity = repository.findById(id);
        if(oEntity.isEmpty()) {
            throw new AccessNotFoundException("Access not found");
        }
        var entity = oEntity.get();
        var customerDto = customerService.getCustomer(dto.getCustomer().getId());
        entity.setAccessDate(dto.getAccessDate());
        entity.setCustomer(customerMapper.toEntity(customerDto));
    }

    @Override
    public void deleteAccess(UUID id) {
        repository.deleteById(id);
    }
}
