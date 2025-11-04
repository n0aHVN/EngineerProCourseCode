package com.redis.demo.service;

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
        String lockValue = UUID.randomUUID().toString();
        Boolean success = redisTemplate.opsForValue().setIfAbsent(lockKey, lockValue, expireSeconds, TimeUnit.SECONDS);
        return Boolean.TRUE.equals(success) ? lockValue : null;
    }

    public void releaseLock(String lockKey, String lockValue) {
        String value = redisTemplate.opsForValue().get(lockKey);
        if (lockValue.equals(value)) {
            redisTemplate.delete(lockKey);
        }
    }
}