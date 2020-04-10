package com.bengodwinweb.pettycash.controller.request;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class PasswordMatchesValidator implements ConstraintValidator<PasswordMatches, Object> {

    @Override
    public void initialize(PasswordMatches constraintAnnotation) {}

    @Override
    public boolean isValid(Object obj, ConstraintValidatorContext constraintValidatorContext) {
        UserSignupRequest user = (UserSignupRequest) obj;
        return user.getPassword().equals(user.getMatchingPassword());
    }
}
