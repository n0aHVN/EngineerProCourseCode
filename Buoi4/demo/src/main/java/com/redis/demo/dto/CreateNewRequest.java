package com.redis.demo.dto;

import lombok.Data;

@Data
public class CreateNewRequest{
    private String title;
    private String slug;
    private String content;
}