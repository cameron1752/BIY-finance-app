package com.biy.finance.app.financeapp.util;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import java.lang.annotation.*;

@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = ValidTransactionValidator.class)
public @interface ValidTransaction {
    String message() default "Invalid transaction";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
