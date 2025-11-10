package com.redis.idempotentTransaction.controller;

import com.redis.idempotentTransaction.dto.ApiResponse;

public abstract class BaseController {
    protected <T> ApiResponse<T> ok(T data) {
        return ApiResponse.<T>builder()
            .status(200)
            .message("Success")
            .data(data)
            .build();
    }
}