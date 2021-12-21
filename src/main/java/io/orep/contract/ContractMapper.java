package io.orep.contract;

import org.mapstruct.Mapper;

@Mapper(componentModel = "cdi")
public interface ContractMapper {

    ContractEntity toEntity(Contract domain);

    Contract toDomain(ContractEntity entity);

}