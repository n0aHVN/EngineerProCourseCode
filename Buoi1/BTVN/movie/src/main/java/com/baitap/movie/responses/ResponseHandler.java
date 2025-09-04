package com.baitap.movie.responses;

import org.springframework.http.ResponseEntity;

public class ResponseHandler {
    public static <T> ResponseEntity<ApiResponse<T>> generateResponse(int status, String message, T data) {
        ApiResponse<T> response = new ApiResponse<>(status, message, data);
        return ResponseEntity.status(status).body(response);
    }
}
