package com.redis.idempotentTransaction.service;

import java.time.Duration;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class IdempotencyService {
    private final RedisTemplate<String, Object> redisTemplate;

    public void storeResponse(String key, Object response){
        redisTemplate.opsForValue().set(key, response, Duration.ofMinutes(10));
    }
    
    public Object getStoredResponse(String key){
        return redisTemplate.opsForValue().get(key);
    }
}