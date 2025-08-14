package com.core.system.core_system_back.exception;

public class DuplicateAppCompanyException extends RuntimeException {

    public DuplicateAppCompanyException(String message) {
        super(message);
    }

    public DuplicateAppCompanyException(String message, Throwable cause) {
        super(message, cause);
    }
} 