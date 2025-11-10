package com.redis.idempotentTransaction.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import com.redis.idempotentTransaction.model.Account;
import com.redis.idempotentTransaction.service.AccountService;

import jakarta.validation.Valid;

import com.redis.idempotentTransaction.dto.ApiResponse;
import com.redis.idempotentTransaction.dto.CreateAccountRequest;

@RestController
@RequestMapping("/api/account")
@RequiredArgsConstructor
public class AccountController {

	private final AccountService accountService;

	@GetMapping("/{id}")
	public ApiResponse<Account> getAccountById(@PathVariable int id) {
		Account account = accountService.getAccountById(id);
		return ApiResponse.success(account);
	}

	@PostMapping()
	public ApiResponse<Account> createAccount(@RequestBody @Valid CreateAccountRequest account) {
		Account saved = accountService.createAccount(
            account.getAccount(),
            account.getFullName(),
            account.getBalance()
        );
		return ApiResponse.success(saved);
	}

	@GetMapping()
	public ApiResponse<List<Account>> getAllAccounts() {
		List<Account> accounts = accountService.getAllAccounts();
		return ApiResponse.success(accounts);
	}
}
