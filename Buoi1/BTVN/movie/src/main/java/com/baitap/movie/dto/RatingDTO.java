package com.baitap.movie.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RatingDTO {
    @NotNull(message = "User ID cannot be null")
    private int userId;
    @NotNull(message = "Movie ID cannot be null")
    private int movieId;
    @Min(value = 1, message = "Rating must be at least 1")
    @Min(value = 5, message = "Rating must be at least 5")
    private int rating;
}

