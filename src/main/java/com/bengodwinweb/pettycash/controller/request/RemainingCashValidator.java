package com.bengodwinweb.pettycash.controller.request;


import com.bengodwinweb.pettycash.dto.model.CashboxDto;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class RemainingCashValidator implements ConstraintValidator<ValidRemaining, Object> {

    @Override
    public void initialize(ValidRemaining constraintAnnotation) {}

    @Override
    public boolean isValid(Object o, ConstraintValidatorContext constraintValidatorContext) {
        CashboxDto cashboxDto = (CashboxDto) o;
        if (cashboxDto.getRemainingCash() == null || cashboxDto.getTotal() == null) return false;
        return cashboxDto.getRemainingCash() <= cashboxDto.getTotal();
    }
}
