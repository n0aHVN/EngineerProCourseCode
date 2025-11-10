package com.redis.idempotentTransaction.service;

import java.time.Duration;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

@Service
public class DistributedLockService {
    private final RedisTemplate<String, Object> redisTemplate;

    public DistributedLockService(RedisTemplate<String, Object> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    public boolean tryLock(String key) {
        Boolean success = redisTemplate.opsForValue().setIfAbsent(key, "true");
        if (Boolean.TRUE.equals(success)) {
            redisTemplate.expire(key, Duration.ofMinutes(5));
        }
        return Boolean.TRUE.equals(success);
    }

    public void releaseLock(String key) {
        redisTemplate.delete(key);
    }

}
