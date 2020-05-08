package com.bengodwinweb.pettycash.controller.request;

import java.util.ArrayList;
import java.util.List;

public class ValidationResult {
    private List<String> errors;
    private boolean hasErrors;

    public ValidationResult() {
        this.hasErrors = false;
        errors = new ArrayList<>();
    }

    public void addError(String errorMesage) {
        errors.add(errorMesage);
        hasErrors = true;
    }

    public List<String> getErrors() {
        return errors;
    }

    public boolean hasErrors() {
        return hasErrors;
    }
}
