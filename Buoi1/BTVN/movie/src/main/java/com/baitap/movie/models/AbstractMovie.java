package com.baitap.movie.models;

import java.util.concurrent.CopyOnWriteArrayList;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data   // generates getters, setters, toString, equals, hashCode
@NoArgsConstructor
@SuperBuilder
public abstract class AbstractMovie{
    private int id;
    private String title;
    private String description;
    private CopyOnWriteArrayList<String> directors;
    private CopyOnWriteArrayList<String> actors;
    private String type; // "single" | "serie"
}
