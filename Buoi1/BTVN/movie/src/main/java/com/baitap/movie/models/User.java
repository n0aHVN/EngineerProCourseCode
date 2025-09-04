package com.baitap.movie.models;

import java.util.concurrent.CopyOnWriteArrayList;

import lombok.Data;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder
public class User {
    private int id;
    private String username;
    private CopyOnWriteArrayList<Integer> favoriteMoviesId;
}
