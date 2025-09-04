package com.baitap.movie.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserFavoriteRequestDTO {
    @NotNull(message = "Username cannot be null")
    private String username;
    @NotNull(message = "Movie ID cannot be null")
    private int movieId;
}
