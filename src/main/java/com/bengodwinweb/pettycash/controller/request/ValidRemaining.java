package com.bengodwinweb.pettycash.controller.request;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = RemainingCashValidator.class)
public @interface ValidRemaining {
    String message() default "Remaining cash must be less than or equal to box total";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
