package com.redis.idempotentTransaction.dto;

import java.time.Instant;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ApiResponse<T> {
    private int status;
    private String message;
    private T data;
    private Instant timestamp;

    public static <T> ApiResponse<T> success(T data){
        ApiResponse<T> response = ApiResponse.<T>builder()
            .status(200)
            .message("Success")
            .data(data)
            .timestamp(Instant.now())
            .build();
        return response;
    }

    public static <T> ApiResponse<T> failure(int status, String message){
        ApiResponse<T> response = ApiResponse.<T>builder()
            .status(status)
            .message(message)
            .data(null)
            .timestamp(Instant.now())
            .build();
        return response;
    }
}
