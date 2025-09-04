package com.baitap.movie.services;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.baitap.movie.models.AbstractMovie;
import com.baitap.movie.models.Rating;
import com.baitap.movie.models.User;

import jakarta.annotation.PostConstruct;

@Service
public class RatingService {
    private static CopyOnWriteArrayList<Rating> ratings = new CopyOnWriteArrayList<>();
    private final MovieService movieService;
    private final UserService userService;

    @Autowired
    public RatingService(MovieService movieService, UserService userService){
        this.movieService = movieService;
        this.userService = userService;
    }

    @PostConstruct
    public void initRatings(){
        Rating rating1 = Rating.builder()
            .id(0)
            .userId(0)
            .movieId(0)
            .rating(5)
            .build();
        Rating rating2 = Rating.builder()
            .id(1)
            .userId(1)
            .movieId(0)
            .rating(5)
            .build();
        Rating rating3 = Rating.builder()
            .id(2)
            .userId(2)
            .movieId(0)
            .rating(5)
            .build();
        ratings.add(rating1);
        ratings.add(rating2);
        ratings.add(rating3);
        
    }
    
    public List<Rating> getRatingsByMovieId(int id){
        CopyOnWriteArrayList<Rating> result = new CopyOnWriteArrayList<>();
        for (Rating rating : ratings){
            if (rating.getMovieId() == id){
                result.add(rating);
            }
        }
        return Collections.unmodifiableList(new ArrayList<>(result));
    }

    public Rating addRating(Rating rating){
        AbstractMovie movie = movieService.getMovieById(rating.getId());
        User user = userService.getUserById(rating.getUserId());
        if (movie==null){
            throw new IllegalArgumentException("movieId not found: "+ rating.getMovieId());
        }
        if (user==null){
            throw new IllegalArgumentException("userId not found: "+ rating.getUserId());
        }

        if (ratings.size() == 0 || ratings.isEmpty()){
            rating.setId(0);    
        }
        else {
            int lastId = ratings.getLast().getId();
            rating.setId(lastId+1);
        }
        ratings.add(rating);
        return rating;
    }
    public void deleteRatingById(int id){
        for (int i = 0; i < ratings.size();i++){
            if (ratings.get(i).getId() == id){
                ratings.remove(i);
                return;
            }
        }
        throw new IllegalArgumentException("Movie not found: "+ id);
    }

}
