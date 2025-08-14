package com.core.system.core_system_back.exception;

public class JwtTokenExpiredException extends RuntimeException {
    
    public JwtTokenExpiredException(String message) {
        super(message);
    }
    
    public JwtTokenExpiredException(String message, Throwable cause) {
        super(message, cause);
    }
} 