package com.core.system.core_system_back.exception;

public class CompanyNotFoundException extends RuntimeException {
    
    public CompanyNotFoundException(String message) {
        super(message);
    }
    
    public CompanyNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
} 