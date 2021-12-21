package io.orep.contract;

import io.orep.exception.ServiceException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import javax.enterprise.context.ApplicationScoped;
import javax.transaction.Transactional;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@ApplicationScoped
@AllArgsConstructor
@Slf4j
public class ContractService {

    private final ContractRepository contractRepository;
    private final ContractMapper contractMapper;

    public List<Contract> findAll() {
        return this.contractRepository.findAll().stream()
                .map(contractMapper::toDomain)
                .collect(Collectors.toList());
    }

    public Optional<Contract> findById(@NotNull Integer contractId) {
        return this.contractRepository.findByIdOptional(contractId)
                .map(contractMapper::toDomain);
    }

    @Transactional
    public Contract save(@Valid Contract contract) {
        log.debug("{}", contract);
        ContractEntity contractEntity = this.contractMapper.toEntity(contract);
        this.contractRepository.persist(contractEntity);
        return this.contractMapper.toDomain(contractEntity);
    }

    @Transactional
    public Contract update(@Valid Contract contract) {
        log.debug("{}", contract);
        ContractEntity contractEntity = this.contractRepository.findByIdOptional(contract.getContractId())
                .orElseThrow(() -> new ServiceException("No Contract found for contractId[%s]", contract.getContractId()));
        contractEntity.setStartDate(contract.getStartDate());
        contractEntity.setEndDate(contract.getEndDate());
        contractEntity.setExecutionDate(contract.getExecutionDate());
        this.contractRepository.persist(contractEntity);
        return this.contractMapper.toDomain(contractEntity);
    }

}