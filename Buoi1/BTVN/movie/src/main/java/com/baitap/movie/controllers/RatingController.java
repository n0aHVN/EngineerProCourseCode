package com.baitap.movie.controllers;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.baitap.movie.dto.RatingDTO;
import com.baitap.movie.models.Rating;
import com.baitap.movie.responses.ApiResponse;
import com.baitap.movie.responses.ResponseHandler;
import com.baitap.movie.services.RatingService;

import jakarta.validation.Valid;
@RestController
public class RatingController {
    private final RatingService ratingService;
    @Autowired
    public RatingController(RatingService ratingService){
        this.ratingService = ratingService;
    }

    @GetMapping("/api/ratings/{movieId}")
    public ResponseEntity<ApiResponse<List<Rating>>> getRatingByMovieId(@PathVariable int movieId) {
        List<Rating> rating = ratingService.getRatingsByMovieId(movieId);
        return ResponseHandler.generateResponse(HttpStatus.OK.value(), null, rating);
    }

    @PostMapping("/api/ratings")
    public ResponseEntity<ApiResponse<Rating>> addRating(@RequestBody @Valid RatingDTO request){
        Rating rating = Rating.builder()
                            .userId(request.getUserId())
                            .movieId(request.getMovieId())
                            .rating(request.getRating())
                            .build();

        rating = this.ratingService.addRating(rating);
        return ResponseHandler.generateResponse(HttpStatus.CREATED.value(), null, rating);
    }

    

}
