package com.v8n.modules.core.infrastructure.config;

import com.v8n.modules.core.application.exception.ErrorCode;
import lombok.Getter;

import java.util.Map;

@Getter
public class BusinessValidationException extends RuntimeException {
    private final ErrorCode errorCode;
    private final Map<String, String> errors;

    public BusinessValidationException(String message) {
        super(message);
        this.errorCode = ErrorCode.INVALID_REQUEST;
        this.errors = null;
    }

    public BusinessValidationException(ErrorCode errorCode) {
        super(errorCode.getMessage());
        this.errorCode = errorCode;
        this.errors = null;
    }

    public BusinessValidationException(ErrorCode errorCode, String message) {
        super(message);
        this.errorCode = errorCode;
        this.errors = null;
    }

    public BusinessValidationException(String message, Map<String, String> errors) {
        super(message);
        this.errorCode = ErrorCode.INVALID_REQUEST;
        this.errors = errors;
    }

    public BusinessValidationException(ErrorCode errorCode, String message, Map<String, String> errors) {
        super(message);
        this.errorCode = errorCode;
        this.errors = errors;
    }
}