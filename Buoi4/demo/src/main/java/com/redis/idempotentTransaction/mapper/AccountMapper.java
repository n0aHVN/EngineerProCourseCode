package com.redis.idempotentTransaction.mapper;

import com.redis.idempotentTransaction.dto.AccountResponse;
import com.redis.idempotentTransaction.model.Account;

public class AccountMapper {
    public static AccountResponse toResponse(Account account){
        return AccountResponse.builder()
            .full_name(account.getFullName())
            .balance(account.getBalance())
            .build();
    }
}
