package com.redis.lock.controller;

import org.springframework.web.bind.annotation.RestController;

import com.redis.lock.dto.CreateNewRequest;
import com.redis.lock.model.New;
import com.redis.lock.service.NewService;

import lombok.RequiredArgsConstructor;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@RestController
@RequestMapping("/api/news")
@RequiredArgsConstructor
public class NewController {
    private final NewService newService;
    @GetMapping("/{slug}")
    public New getNewBySlug(@PathVariable String slug) {
        return newService.getNewBySlug(slug);
    }
    
    @PostMapping()
    public New postNew(@RequestBody CreateNewRequest entity) {
		New newNew = New.builder()
				.title(entity.getTitle())
				.slug(entity.getSlug())
				.content(entity.getContent())
				.build();
        
		newService.createNews(newNew);
        return newNew;
    }
    
    public New createNews(New news) {
        return newService.createNews(news);
    }
}
