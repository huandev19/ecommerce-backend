package com.v8n.modules.core.infrastructure.config;

import com.v8n.modules.core.application.exception.ErrorCode;
import lombok.Getter;

@Getter
public class ResourceNotFoundException extends RuntimeException {
    private final ErrorCode errorCode;

    public ResourceNotFoundException(String resourceName, String fieldName, Object fieldValue) {
        super(String.format("%s not found with %s: '%s'", resourceName, fieldName, fieldValue));
        this.errorCode = ErrorCode.RESOURCE_NOT_FOUND;
    }

    public ResourceNotFoundException(ErrorCode errorCode) {
        super(errorCode.getMessage());
        this.errorCode = errorCode;
    }

    public ResourceNotFoundException(ErrorCode errorCode, String message) {
        super(message);
        this.errorCode = errorCode;
    }
}