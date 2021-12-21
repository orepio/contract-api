package io.orep.contract;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.Objects;

public class ContractDatesValidator implements ConstraintValidator<ContractDatesValid, Contract> {

    @Override
    public void initialize(ContractDatesValid constraintAnnotation) {
    }

    @Override
    public boolean isValid(Contract contract, ConstraintValidatorContext constraintValidatorContext) {
        if (Objects.isNull(contract.getStartDate()) || Objects.isNull(contract.getEndDate())){
            return false;
        }
        return contract.getStartDate().isBefore(contract.getEndDate());
    }

}
