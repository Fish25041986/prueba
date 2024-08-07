package com.thomasgregsons.ecommerce.exception;

import java.time.LocalDateTime;
import java.util.Map;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ErrorResponse {
    private LocalDateTime timestamp;
    private int status;
    private String error;
    private String path;
    private Map<String, String> validationErrors;
    private String methodInError;
    private String message;

    public ErrorResponse() {
    	
    }
}
