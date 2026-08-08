package com.biy.finance.app.financeapp.util;

import com.biy.finance.app.financeapp.model.Transaction;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class ValidTransactionValidator implements ConstraintValidator<ValidTransaction, Transaction> {

    @Override
    public boolean isValid(Transaction request, ConstraintValidatorContext context) {
        if (request == null) return true; // let @NotNull handle null-ness separately

        boolean valid = true;

       // implementation of bean n stuff

        // account id
//        if (validateString(request.getAccountId())){
//            context.disableDefaultConstraintViolation();
//            context.buildConstraintViolationWithTemplate("accountId must not be empty")
//                    .addPropertyNode("accountId")
//                    .addConstraintViolation();
//            valid = false;
//        }

        // date
        if (request.getDate() == null){
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate("date must not be empty")
                    .addPropertyNode("date")
                    .addConstraintViolation();
            valid = false;
        }

        // category
        if (validateString(request.getCategory())){
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate("category must not be empty")
                    .addPropertyNode("category")
                    .addConstraintViolation();
            valid = false;
        }

        return valid;
    }

    private boolean validateString(String value){
        if (value == null || value.isBlank() || value.isEmpty() ){
            return true;
        } else {
            return false;
        }
    }

}
