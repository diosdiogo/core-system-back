package com.core.system.core_system_back.exception;

public class DuplicateResponsibilityException extends RuntimeException {
    
    public DuplicateResponsibilityException(String message) {
        super(message);
    }
    
    public DuplicateResponsibilityException(String message, Throwable cause) {
        super(message, cause);
    }
} 