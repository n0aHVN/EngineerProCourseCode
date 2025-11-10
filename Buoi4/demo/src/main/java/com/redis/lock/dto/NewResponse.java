package com.redis.lock.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class NewResponse{
    private String title;
    private String slug;
    private String content;
}