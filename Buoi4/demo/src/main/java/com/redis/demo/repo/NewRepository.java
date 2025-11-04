package com.redis.demo.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.redis.demo.model.New;

public interface NewRepository extends JpaRepository<New, Integer> {
    public New findBySlug(String slug);
}
