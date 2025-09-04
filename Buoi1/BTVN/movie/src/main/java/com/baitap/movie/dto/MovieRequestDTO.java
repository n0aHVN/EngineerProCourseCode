package com.baitap.movie.dto;

import java.util.List;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.Setter;
@Getter
@Setter
public class MovieRequestDTO {
    @NotBlank(message = "Title is required")
    private String title;
    @NotBlank(message = "Description is required")
    private String description;
    @NotNull(message = "Directors are required")
    private List<String> directors;
    @NotNull(message = "Actors are required")
    private List<String> actors;
    @NotBlank(message="Type is required")
    @Pattern(regexp = "single|serie", message = "Type must be 'single' or 'serie'")
    private String type; // "single" | "serie"

    //Single Movie
    private Integer duration_in_minutes;

    //Serie Movie
    private Integer number_of_seasons;

    private Integer number_of_episodes;
}
