package io.orep.contract;

import io.quarkus.hibernate.orm.panache.PanacheRepositoryBase;

import javax.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class ContractRepository implements PanacheRepositoryBase<ContractEntity, Integer> {
}