package com.core.system.core_system_back.exception;

public class AppNotFoundException extends RuntimeException {

    public AppNotFoundException(String message) {
        super(message);
    }

    public AppNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
} 