package com.redis.idempotentTransaction.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;
import java.math.BigDecimal;

@Data
@Builder
public class CreateAccountRequest {
    @NotBlank(message = "Missing account")
    private String account;

    @NotBlank(message = "Missing fullName")
    private String fullName;

    @NotNull(message = "Missing balance")
    private BigDecimal balance;
}
