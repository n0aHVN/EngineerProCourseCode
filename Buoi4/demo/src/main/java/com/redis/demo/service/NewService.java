package com.redis.demo.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;

import com.redis.demo.model.New;
import com.redis.demo.repo.NewRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class NewService {
    private final NewRepository newRepos;
    @Autowired
    private DistributedLockService distributedLockService;

    public List<New> getAllNews() {
        return newRepos.findAll();
    }
    
    public New createNews(New news) {
        String lockKey = "news:create:lock";
        String lockValue = distributedLockService.acquireLock(lockKey, 30);
        if (lockValue == null) {
            throw new RuntimeException("Could not acquire lock for creating news");
        }
        try {
            // Simulate some processing time
            Thread.sleep(5000);
            return newRepos.save(news);
        }
        catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new RuntimeException("Thread was interrupted", e);
        } 
        finally {
            distributedLockService.releaseLock(lockKey, lockValue);
        }
    }

    public int deleteNews(int id) {
        String lockKey = "news:" + id + ":lock";
        String lockValue = distributedLockService.acquireLock(lockKey, 30);
        if (lockValue == null) {
            throw new RuntimeException("Could not acquire lock for deleting news id=" + id);
        }
        try {
            newRepos.deleteById(id);
            return id;
        } finally {
            distributedLockService.releaseLock(lockKey, lockValue);
        }
    }

    public New getNewBySlug(String slug) {
        return newRepos.findBySlug(slug);
    }
}
