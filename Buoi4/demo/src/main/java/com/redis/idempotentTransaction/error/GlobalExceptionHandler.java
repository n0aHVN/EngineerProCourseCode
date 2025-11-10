package com.redis.idempotentTransaction.error;

import com.redis.idempotentTransaction.dto.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.http.converter.HttpMessageNotReadableException;

/**
 * Global exception handler for REST APIs. Catches exceptions thrown by controllers and returns a consistent ApiResponse structure.
 */

@RestControllerAdvice
public class GlobalExceptionHandler {

    /**
     * Handles JSON parse/type errors (e.g., wrong type for a field like string for BigDecimal).
     * Returns a 400 BAD REQUEST with a custom message.
     */
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ApiResponse<Void> handleTypeMismatch(HttpMessageNotReadableException ex) {
        String msg = "Invalid request: wrong type for one or more fields.";
        return ApiResponse.<Void>builder()
                .status(HttpStatus.BAD_REQUEST.value())
                .message(msg)
                .data(null)
                .timestamp(java.time.Instant.now())
                .build();
    }

    /**
     * Handles NotFoundError exceptions (custom business exception for not found resources).
     * Returns a 404 NOT FOUND response with the error message.
     */
    @ExceptionHandler(NotFoundError.class)
    public ApiResponse<Void> handleNotFound(NotFoundError ex) {
    return ApiResponse.<Void>builder()
        .status(HttpStatus.NOT_FOUND.value()) // 404
        .message(ex.getMessage()) // Use the exception's message
        .data(null) // No data for errors
        .timestamp(java.time.Instant.now()) // Current time
        .build();
    }

    /**
     * Handles validation errors (e.g., @Valid/@Validated on request bodies).
     * Returns a 400 BAD REQUEST with the first validation error message.
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ApiResponse<Void> handleValidation(MethodArgumentNotValidException ex) {
    String msg = ex.getBindingResult().getAllErrors().stream()
        .map(e -> e.getDefaultMessage())
        .findFirst().orElse("Validation error");
    return ApiResponse.<Void>builder()
        .status(HttpStatus.BAD_REQUEST.value()) // 400
        .message(msg) // First validation error message
        .data(null)
        .timestamp(java.time.Instant.now())
        .build();
    }

    /**
     * Handles all other uncaught exceptions.
     * Returns a 500 INTERNAL SERVER ERROR with a generic message.
     */
    @ExceptionHandler(Exception.class)
    public ApiResponse<Void> handleOther(Exception ex) {
        return ApiResponse.<Void>builder()
                .status(HttpStatus.INTERNAL_SERVER_ERROR.value()) // 500
                .message("Internal server error") // Hide internal details
                .data(null)
                .timestamp(java.time.Instant.now())
                .build();
    }
}
