package com.redis.idempotentTransaction.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CreateTransactionRequest {
    @NotBlank(message="Missing fromAccount")
    private String fromAccount;
    @NotBlank(message="Missing toAccount")
    private String toAccount;
    @NotNull(message="Missing amount")
    private double amount;
    @NotBlank(message="Missing idempotencyKey")
    private String idempotencyKey;
}
