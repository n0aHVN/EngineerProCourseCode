package com.baitap.movie.services;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Vector;
import java.util.concurrent.CopyOnWriteArrayList;

import org.springframework.stereotype.Service;

import com.baitap.movie.models.AbstractMovie;
import com.baitap.movie.models.SerieMovies;
import com.baitap.movie.models.SingleMovie;

import jakarta.annotation.PostConstruct;
import lombok.Getter;

@Service
public class MovieService {
    @Getter
    private static CopyOnWriteArrayList<AbstractMovie> movies = new CopyOnWriteArrayList<>();
    @PostConstruct
    public void initMovies(){
        SingleMovie movie1 = SingleMovie.builder()
            .id(0)
            .title("Single Movie !")
            .description("Description 1")
            .directors(new CopyOnWriteArrayList<>(List.of("Director 1")))
            .actors(new CopyOnWriteArrayList<>(List.of("Actor 1", "Actor 2")))
            .duration_in_minutes(180)
            .build();
        SingleMovie movie2 = SingleMovie.builder()
            .id(1)
            .title("Single Movie !")
            .description("Description 1")
            .directors(new CopyOnWriteArrayList<>(List.of("Director 1")))
            .actors(new CopyOnWriteArrayList<>(List.of("Actor 1", "Actor 2")))
            .duration_in_minutes(180)
            .build();
        SerieMovies movie3 = SerieMovies.builder()
            .id(2)
            .title("Serie Movie !")
            .description("Description 1")
            .directors(new CopyOnWriteArrayList<>(List.of("Director 1")))
            .actors(new CopyOnWriteArrayList<>(List.of("Actor 1", "Actor 2")))
            .number_of_episodes(10)
            .number_of_seasons(2)
            .build();
        movies.add(movie1);
        movies.add(movie2);
        movies.add(movie3);

    }
    public AbstractMovie getMovieById(int id){
        for (AbstractMovie movie : movies){
            if (movie.getId() == id){
                return movie;
            }
        }
        throw new IllegalArgumentException("movieId not found: "+ id);
    }
    public void deleteMovieById(int id){
        AbstractMovie movie = movies.stream()
                                    .filter((r) -> r.getId() == r.getId())
                                    .findFirst()
                                    .orElseThrow(() -> new IllegalArgumentException("movieId not found: "+ id));
        movies.remove(movie);
    }

    public List<AbstractMovie> getAllMovies(){
        return Collections.unmodifiableList(new ArrayList<>(movies));
    }

    public AbstractMovie addMovie(AbstractMovie movie) {
        if (movies.size() == 0 || movies.isEmpty()){
            movie.setId(0);
        } else {
            int lastId = movies.getLast().getId();
            movie.setId(lastId + 1);
        }
        movies.add(movie);
        return movie;
    }
    public boolean isMovieExist(int movieId) {
        return movies.stream().anyMatch(movie -> movie.getId() == movieId);
    }

}
