package com.redis.idempotentTransaction.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PathVariable;
import lombok.RequiredArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

import com.redis.idempotentTransaction.dto.ApiResponse;
import com.redis.idempotentTransaction.dto.CreateTransactionRequest;
import com.redis.idempotentTransaction.model.Transaction;
import com.redis.idempotentTransaction.service.TransactionService;

import jakarta.validation.Valid;


@RestController
@RequestMapping("/api/transaction")
@RequiredArgsConstructor
public class TransactionController {

    private final TransactionService transactionService;


    @GetMapping("/{id}")
    public ApiResponse<Transaction> getTransactionById(@PathVariable int id) {
        Transaction transaction = transactionService.getTransactionById(id);
        return ApiResponse.success(transaction);
    }


    @PostMapping()
    public ApiResponse<Transaction> createTransaction(@RequestBody @Valid CreateTransactionRequest request) {
        Transaction saved = transactionService.createTransaction(
            BigDecimal.valueOf(request.getAmount()),
            request.getFromAccount(),
            request.getToAccount(),
            request.getIdempotencyKey()
        );
        return ApiResponse.success(saved);
    }

    @GetMapping()
    public ApiResponse<List<Transaction>> getAllTransactions() {
        List<Transaction> transactions = transactionService.getAllTransactions();
        return ApiResponse.success(transactions);
    }
}
