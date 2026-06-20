package com.v8n.modules.core.infrastructure.config;

import com.v8n.modules.core.application.dto.ApiResponse;
import com.v8n.modules.core.application.exception.BusinessException;
import com.v8n.modules.core.application.exception.ErrorCode;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.TypeMismatchException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.NoHandlerFoundException;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(BusinessException.class)
    public ResponseEntity<ApiResponse<Void>> handleBusinessException(BusinessException ex) {
        HttpStatus status = resolveStatus(ex.getErrorCode());
        log.warn("Business exception [{}]: {}", ex.getErrorCode(), ex.getMessage());
        return ResponseEntity
                .status(status)
                .body(ApiResponse.error(ex.getMessage()));
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ApiResponse<Void>> handleResourceNotFound(ResourceNotFoundException ex) {
        log.warn("Resource not found [{}]: {}", ex.getErrorCode(), ex.getMessage());
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(ApiResponse.error(ex.getMessage()));
    }

    @ExceptionHandler(BusinessValidationException.class)
    public ResponseEntity<ApiResponse<Void>> handleBusinessValidation(BusinessValidationException ex) {
        log.warn("Validation exception [{}]: {}", ex.getErrorCode(), ex.getMessage());
        return ResponseEntity
                .badRequest()
                .body(ApiResponse.error(ex.getMessage(), ex.getErrors()));
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiResponse<Void>> handleValidationExceptions(MethodArgumentNotValidException ex) {
        Map<String, String> errors = extractFieldErrors(ex.getBindingResult().getFieldErrors());
        log.warn("Validation failed: {}", errors);
        return ResponseEntity
                .badRequest()
                .body(ApiResponse.error("Validation failed", errors));
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<ApiResponse<Void>> handleConstraintViolation(ConstraintViolationException ex) {
        Map<String, String> errors = ex.getConstraintViolations().stream()
                .collect(Collectors.toMap(
                        violation -> violation.getPropertyPath().toString(),
                        ConstraintViolation::getMessage,
                        (first, second) -> first
                ));
        log.warn("Constraint violation: {}", errors);
        return ResponseEntity
                .badRequest()
                .body(ApiResponse.error("Constraint violation", errors));
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ApiResponse<Void>> handleHttpMessageNotReadable(HttpMessageNotReadableException ex) {
        log.warn("Malformed request body: {}", ex.getMessage());
        return ResponseEntity
                .badRequest()
                .body(ApiResponse.error("Malformed request body"));
    }

    @ExceptionHandler(MissingServletRequestParameterException.class)
    public ResponseEntity<ApiResponse<Void>> handleMissingServletRequestParameter(MissingServletRequestParameterException ex) {
        Map<String, String> errors = Map.of(ex.getParameterName(), "Required request parameter is missing");
        log.warn("Missing request parameter: {}", errors);
        return ResponseEntity
                .badRequest()
                .body(ApiResponse.error("Missing request parameter", errors));
    }

    @ExceptionHandler(TypeMismatchException.class)
    public ResponseEntity<ApiResponse<Void>> handleTypeMismatch(TypeMismatchException ex) {
        String field = ex.getPropertyName() != null ? ex.getPropertyName() : "value";
        Map<String, String> errors = Map.of(field, "Invalid value type");
        log.warn("Type mismatch: {}", ex.getMessage());
        return ResponseEntity
                .badRequest()
                .body(ApiResponse.error("Invalid value type", errors));
    }

    @ExceptionHandler(BindException.class)
    public ResponseEntity<ApiResponse<Void>> handleBindException(BindException ex) {
        Map<String, String> errors = extractFieldErrors(ex.getBindingResult().getFieldErrors());
        log.warn("Binding failed: {}", errors);
        return ResponseEntity
                .badRequest()
                .body(ApiResponse.error("Binding failed", errors));
    }

    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<ApiResponse<Void>> handleBadCredentials(BadCredentialsException ex) {
        log.warn("Bad credentials: {}", ex.getMessage());
        return ResponseEntity
                .status(HttpStatus.UNAUTHORIZED)
                .body(ApiResponse.error("Invalid credentials"));
    }

    @ExceptionHandler(AuthenticationException.class)
    public ResponseEntity<ApiResponse<Void>> handleAuthentication(AuthenticationException ex) {
        log.warn("Authentication failed: {}", ex.getMessage());
        return ResponseEntity
                .status(HttpStatus.UNAUTHORIZED)
                .body(ApiResponse.error("Authentication required"));
    }

    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<ApiResponse<Void>> handleAccessDenied(AccessDeniedException ex) {
        log.warn("Access denied: {}", ex.getMessage());
        return ResponseEntity
                .status(HttpStatus.FORBIDDEN)
                .body(ApiResponse.error("Access denied"));
    }

    @ExceptionHandler(NoHandlerFoundException.class)
    public ResponseEntity<ApiResponse<Void>> handleNoHandlerFound(NoHandlerFoundException ex) {
        log.warn("No handler found: {} {}", ex.getHttpMethod(), ex.getRequestURL());
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(ApiResponse.error("Resource not found"));
    }

    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public ResponseEntity<ApiResponse<Void>> handleMethodNotSupported(HttpRequestMethodNotSupportedException ex) {
        log.warn("Method not allowed: {}", ex.getMessage());
        return ResponseEntity
                .status(HttpStatus.METHOD_NOT_ALLOWED)
                .body(ApiResponse.error("Method not allowed"));
    }

    @ExceptionHandler(HttpMediaTypeNotSupportedException.class)
    public ResponseEntity<ApiResponse<Void>> handleMediaTypeNotSupported(HttpMediaTypeNotSupportedException ex) {
        log.warn("Unsupported media type: {}", ex.getMessage());
        return ResponseEntity
                .status(HttpStatus.UNSUPPORTED_MEDIA_TYPE)
                .body(ApiResponse.error("Unsupported media type"));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiResponse<Void>> handleGeneralException(Exception ex) {
        log.error("Unexpected error", ex);
        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(ApiResponse.error("Internal server error"));
    }

    private Map<String, String> extractFieldErrors(Iterable<FieldError> fieldErrors) {
        Map<String, String> errors = new HashMap<>();
        fieldErrors.forEach(error -> errors.put(error.getField(), error.getDefaultMessage()));
        return errors;
    }

    private HttpStatus resolveStatus(ErrorCode errorCode) {
        return switch (errorCode) {
            case RESOURCE_NOT_FOUND,
                    USER_NOT_FOUND,
                    CUSTOMER_NOT_FOUND,
                    ROLE_NOT_FOUND,
                    CART_NOT_FOUND,
                    LINE_ITEM_NOT_FOUND,
                    RESERVATION_NOT_FOUND,
                    INVENTORY_ITEM_NOT_FOUND,
                    ORDER_NOT_FOUND,
                    PAYMENT_SESSION_NOT_FOUND,
                    SHIPPING_OPTION_NOT_FOUND,
                    SHIPPING_PROFILE_NOT_FOUND,
                    DISCOUNT_NOT_FOUND,
                    PRODUCT_NOT_FOUND,
                    CATEGORY_NOT_FOUND,
                    VARIANT_NOT_FOUND,
                    STORE_NOT_FOUND,
                    REGION_NOT_FOUND -> HttpStatus.NOT_FOUND;
            case ACCESS_DENIED,
                    PAYMENT_NOT_AUTHORIZED -> HttpStatus.FORBIDDEN;
            case INVALID_CREDENTIALS -> HttpStatus.UNAUTHORIZED;
            case EMAIL_ALREADY_EXISTS -> HttpStatus.CONFLICT;
            case INTERNAL_ERROR -> HttpStatus.INTERNAL_SERVER_ERROR;
            default -> HttpStatus.BAD_REQUEST;
        };
    }
}
