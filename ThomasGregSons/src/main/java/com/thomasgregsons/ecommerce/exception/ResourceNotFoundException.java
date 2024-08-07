package com.thomasgregsons.ecommerce.exception;

public class ResourceNotFoundException extends RuntimeException {
	
	private String methodInError;

	public ResourceNotFoundException(String methodInError, String message) {
        super(message);
        this.methodInError = methodInError;
    }

	public String getMethodInError() {
		return methodInError;
	}

    public void setMethodInError(String methodInError) {
        this.methodInError = methodInError;
    }	
	
}
