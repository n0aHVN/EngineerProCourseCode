package com.redis.idempotentTransaction.service;

import com.redis.idempotentTransaction.error.NotFoundError;
import com.redis.idempotentTransaction.model.Transaction;
import com.redis.idempotentTransaction.repo.TransactionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TransactionService {
    private final TransactionRepository transactionRepository;
    private final DistributedLockService distributedLockService;
    private final IdempotencyService idempotencyService;

    public Transaction getTransactionById(int id) {
        return transactionRepository.findById(id)
                .orElseThrow(() -> new NotFoundError("Transaction not found with id: " + id));
    }

    public Transaction createTransaction(BigDecimal amount, String fromAccount, String toAccount,
            String idempotencyKey) {
        // 1. Store the locked idempotency key in Redis
        String lockKey = "lock:transaction:" + idempotencyKey;

        boolean isLocked = distributedLockService.tryLock(lockKey);
        if (!isLocked) {
            throw new RuntimeException("Duplicate transaction request with idempotency key: " + idempotencyKey);
        }
        // 2. Store and return the previous response if the idempotency key exists
        Object idempotentValue = idempotencyService.getStoredResponse(idempotencyKey);
        if (idempotentValue != null) {
            distributedLockService.releaseLock(lockKey);
            return (Transaction) idempotentValue;
        }

        // 3. Create new transaction
        Transaction transaction = Transaction.builder()
                .amount(amount)
                .fromAccount(fromAccount)
                .toAccount(toAccount)
                .idempotentKey(idempotencyKey)
                .build();

        // 4. Store the response in Redis and DB for idempotency
        idempotencyService.storeResponse(idempotencyKey, transaction);
        transactionRepository.save(transaction);
        transactionRepository.flush();
        distributedLockService.releaseLock(lockKey);
        return transaction;

    }

    public List<Transaction> getAllTransactions() {
        return transactionRepository.findAll();
    }
}
