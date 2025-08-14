package com.core.system.core_system_back.exception;

public class JwtTokenInvalidException extends RuntimeException {
    
    public JwtTokenInvalidException(String message) {
        super(message);
    }
    
    public JwtTokenInvalidException(String message, Throwable cause) {
        super(message, cause);
    }
} 