package com.redis.idempotentTransaction.model;

import java.math.BigDecimal;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity(name = "transaction")
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Transaction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(nullable = false)
    private String fromAccount;
    @Column(nullable = false)
    private String toAccount;

    // Unique idempotency key for transaction, char(36) UUID
    @Column(nullable = false, unique = true, length = 36)
    private String idempotentKey;

    //15 means total digits, 2 means digits after decimal point
    @Column(nullable = false, precision = 15, scale = 2)
    private BigDecimal amount;
}
