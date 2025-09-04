package com.baitap.movie.controllers;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.baitap.movie.dto.MovieRequestDTO;
import com.baitap.movie.models.AbstractMovie;
import com.baitap.movie.models.SerieMovies;
import com.baitap.movie.models.SingleMovie;
import com.baitap.movie.responses.ApiResponse;
import com.baitap.movie.responses.ResponseHandler;
import com.baitap.movie.services.MovieService;

import jakarta.validation.Valid;

@RestController
public class MovieController {
    private final MovieService movieService;

    @Autowired
    public MovieController(MovieService movieService){
        this.movieService = movieService;
    }

    @GetMapping("/api/movies/{movieId}")
    public ResponseEntity<ApiResponse<AbstractMovie>> getMovieById(@PathVariable int movieId) {
        AbstractMovie movie = movieService.getMovieById(movieId);
        return ResponseHandler.generateResponse(HttpStatus.OK.value(), null, movie);
    }
    

    @GetMapping("/api/movies")
    public ResponseEntity<ApiResponse<List<AbstractMovie>>> getAllMovies(){
        List<AbstractMovie> movies = movieService.getAllMovies();
        return ResponseHandler.generateResponse(HttpStatus.OK.value(), null, movies);
    }

    @PostMapping("/api/movies")
    public ResponseEntity<ApiResponse<AbstractMovie>> addMovie(@RequestBody @Valid MovieRequestDTO request) {
        AbstractMovie movie;

        // Determine the type of movie and build the appropriate object
        if ("single".equalsIgnoreCase(request.getType())) {
            if (request.getDuration_in_minutes() == null) {
                throw new IllegalArgumentException("Duration in minutes is required for single movies");
            }
            movie = SingleMovie.builder()
                .title(request.getTitle())
                .description(request.getDescription())
                .directors(new CopyOnWriteArrayList<String>(request.getDirectors()))
                .actors(new CopyOnWriteArrayList<String>(request.getActors()))
                .duration_in_minutes(request.getDuration_in_minutes())
                .build();
        } else if ("serie".equalsIgnoreCase(request.getType())) {
            if (request.getNumber_of_seasons() == null) {
                throw new IllegalArgumentException("Number of seasons is required for series");
            }
            if (request.getNumber_of_episodes() == null) {
                throw new IllegalArgumentException("Number of episodes is required for series");
            }
            movie = SerieMovies.builder()
                .title(request.getTitle())
                .description(request.getDescription())
                .directors(new CopyOnWriteArrayList<String>(request.getDirectors()))
                .actors(new CopyOnWriteArrayList<String>(request.getActors()))
                .number_of_seasons(request.getNumber_of_seasons())
                .number_of_episodes(request.getNumber_of_episodes())
                .build();
        } else {
            throw new IllegalArgumentException("Invalid movie type");
        }

        // Add the movie to the service
        movie = movieService.addMovie(movie);

        // Return success response
        return ResponseHandler.generateResponse(HttpStatus.CREATED.value(), "Movie added successfully", movie);
    }
}
