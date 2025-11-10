package com.redis.idempotentTransaction.dto;

import java.math.BigDecimal;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class TransactionResponse {
    private BigDecimal amount;
    private String fromAccount;
    private String fromFullName;
    private String toAccount;
    private String toFullName;
    private String idempotentKey;
}
