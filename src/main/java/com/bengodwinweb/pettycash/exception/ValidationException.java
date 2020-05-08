package com.bengodwinweb.pettycash.exception;

import org.springframework.validation.Errors;

public class ValidationException extends Exception {
    private Errors errors;

    public ValidationException(String message, Errors errors) {
        super(message);
        this.errors = errors;
    }

    public Errors getErrors() {
        return errors;
    }
}
