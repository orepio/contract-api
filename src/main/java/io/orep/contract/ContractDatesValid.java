package io.orep.contract;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Target({ElementType.TYPE, ElementType.ANNOTATION_TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = ContractDatesValidator.class)
@Documented
public @interface ContractDatesValid {

    String message() default "Contract.startDate must be prior to Contract.endDate:  " +
            "startDate[${validatedValue.startDate}], endDate[${validatedValue.endDate}]";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

}
