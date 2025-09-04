package com.baitap.movie.models;

import lombok.Data;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder
public class Rating {
    private int id;
    private int userId;
    private int movieId;
    private int rating;
}
