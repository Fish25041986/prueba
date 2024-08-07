package com.thomasgregsons.ecommerce.exception;

import java.util.Map;

public class ValidationException extends RuntimeException {
    private Map<String, String> validationErrors;

    public ValidationException(String message, Map<String, String> validationErrors) {
        super(message);
        this.validationErrors = validationErrors;
    }

    public Map<String, String> getValidationErrors() {
        return validationErrors;
    }
}
