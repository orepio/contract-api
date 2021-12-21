package io.orep.contract;

import lombok.Data;

import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@Data
@ContractDatesValid
public class Contract {

    private Integer contractId;

    @NotNull(message = "Contract.startDate is null")
    private LocalDate startDate;

    @NotNull(message = "Contract.endDate is null")
    private LocalDate endDate;

    private LocalDate executionDate;

}