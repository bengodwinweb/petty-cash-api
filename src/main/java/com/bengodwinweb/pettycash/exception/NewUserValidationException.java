package com.bengodwinweb.pettycash.exception;

import org.springframework.validation.Errors;

public class NewUserValidationException extends Exception {
    private Errors errors;

    public NewUserValidationException(String message, Errors errors) {
        super(message);
        this.errors = errors;
    }

    public Errors getErrors() {
        return errors;
    }
}
