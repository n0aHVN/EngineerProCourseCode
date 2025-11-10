package com.redis.lock.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.util.UUID;
import java.util.concurrent.TimeUnit;

@Service
public class DistributedLockService {

    @Autowired
    private StringRedisTemplate redisTemplate;

    public String acquireLock(String lockKey, long expireSeconds) {
        String lockValue = UUID.randomUUID().toString(); // Generate a unique lock value to identify the lock owner
        Boolean success = redisTemplate.opsForValue().setIfAbsent(lockKey, lockValue, expireSeconds, TimeUnit.SECONDS); // Try to set the lock if it DOESN'T exist
        return Boolean.TRUE.equals(success) ? lockValue : null;
    }

    public void releaseLock(String lockKey, String lockValue) {
        String value = redisTemplate.opsForValue().get(lockKey);
        if (lockValue.equals(value)) {
            redisTemplate.delete(lockKey);
        }
    }
}