package com.redis.lock.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.redis.lock.service.DistributedLockService;

@RestController
@RequestMapping("/lock")
public class LockController {

    @Autowired
    private DistributedLockService lockService;

    @GetMapping("/acquire")
    public String acquireLock(@RequestParam String key) {
        String lockValue = lockService.acquireLock(key, 30);
        return lockValue != null ? "Lock acquired: " + lockValue : "Lock failed";
    }

    @PostMapping("/release")
    public String releaseLock(@RequestParam String key, @RequestParam String value) {
        lockService.releaseLock(key, value);
        return "Lock released";
    }
}