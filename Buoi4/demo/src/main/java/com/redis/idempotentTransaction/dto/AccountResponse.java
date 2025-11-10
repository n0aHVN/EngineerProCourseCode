package com.redis.idempotentTransaction.dto;

import java.math.BigDecimal;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AccountResponse {
    private String account;
    private String full_name;
    private BigDecimal balance;
}
