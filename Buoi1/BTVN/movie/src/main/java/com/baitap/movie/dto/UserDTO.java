package com.baitap.movie.dto;

import java.util.List;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserDTO {
    @NotBlank(message = "Username cannot be null")
    private String username;
    @NotNull(message = "Favorite movies ID cannot be null")
    private List<Integer> favoriteMoviesId;
}
