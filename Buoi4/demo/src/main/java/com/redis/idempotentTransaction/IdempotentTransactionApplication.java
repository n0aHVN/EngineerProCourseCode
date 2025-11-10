package com.redis.idempotentTransaction;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class IdempotentTransactionApplication {

	public static void main(String[] args) {
		SpringApplication.run(IdempotentTransactionApplication.class, args);
	}

}
