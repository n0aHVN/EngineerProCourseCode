package com.redis.demo.controller;

import org.springframework.web.bind.annotation.RestController;

import com.redis.demo.model.New;
import com.redis.demo.service.NewService;

import lombok.RequiredArgsConstructor;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@RestController
@RequestMapping("/api/news")
@RequiredArgsConstructor
public class NewController {
    private final NewService newService;
    @GetMapping("/{slug}")
    public New getNewBySlug(@PathVariable String slug) {
        return newService.getNewBySlug(slug);
    }
    
}
