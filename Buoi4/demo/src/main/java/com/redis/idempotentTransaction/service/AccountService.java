package com.redis.idempotentTransaction.service;

import com.redis.idempotentTransaction.error.NotFoundError;
import com.redis.idempotentTransaction.model.Account;
import com.redis.idempotentTransaction.repo.AccountRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AccountService {
    private final AccountRepository accountRepository;
    public Account getAccountById(int id) {
        return accountRepository.findById(id)
                .orElseThrow(() -> new NotFoundError("Account not found with id: " + id));
    }

    public Account createAccount(String account, String fullName, BigDecimal balance) {
        Account newAccount = Account.builder()
            .account(account)
            .fullName(fullName)
            .balance(balance)
            .build();
        return accountRepository.save(newAccount);
    }

    public List<Account> getAllAccounts() {
        return accountRepository.findAll();
    }
}
