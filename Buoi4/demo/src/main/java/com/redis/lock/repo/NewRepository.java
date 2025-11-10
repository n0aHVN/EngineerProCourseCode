package com.redis.lock.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.redis.lock.model.New;

public interface NewRepository extends JpaRepository<New, Integer> {
    public New findBySlug(String slug);
}
