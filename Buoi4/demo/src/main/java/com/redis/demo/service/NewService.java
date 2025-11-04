package com.redis.demo.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.redis.demo.model.New;
import com.redis.demo.repo.NewRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class NewService {
    private final NewRepository newRepos;

    public List<New> getAllNews() {
        return newRepos.findAll();
    }
    
    public New createNews(New news) {
        return newRepos.save(news);
    }

    public int deleteNews(int id) {
        newRepos.deleteById(id);
        return id;
    }

    public New getNewBySlug(String slug) {
        return newRepos.findBySlug(slug);
    }
}
