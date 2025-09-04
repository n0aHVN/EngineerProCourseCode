package com.baitap.movie.services;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baitap.movie.models.User;

import jakarta.annotation.PostConstruct;

@Service
public class UserService {
    private static CopyOnWriteArrayList<User> users = new CopyOnWriteArrayList<>();
    private final MovieService movieService;

    @Autowired
    public UserService(MovieService movieService) {
        this.movieService = movieService;
    }

    @PostConstruct
    public void initUsers(){
        User user1 = User.builder().id(0)
            .username("user1")
            .favoriteMoviesId(new CopyOnWriteArrayList<Integer>(List.of(0,1)))
            .build();
        User user2 = User.builder().id(1)
            .username("user2")
            .favoriteMoviesId(new CopyOnWriteArrayList<Integer>(List.of(1,2)))
            .build();
        User user3 = User.builder().id(2)
            .username("user3")
            .favoriteMoviesId(new CopyOnWriteArrayList<Integer>(List.of(0,2)))
            .build();
        users.add(user1);
        users.add(user2);
        users.add(user3);
    }
    public boolean isUsernameTaken(String username) {
        for (User user : users) {
            if (user.getUsername().equals(username)) {
                return true;
            }
        }
        return false;
    }

    public User addUser(User user) {
        if (isUsernameTaken(user.getUsername())){
            throw new IllegalArgumentException("Username is already taken: " + user.getUsername());
        }
        
        int id = 0;
        if (users.isEmpty() || users.size() == 0){
            user.setId(id);
        }
        else {
            id = users.getLast().getId() + 1;
            user.setId(id);
        }
        users.add(user);
        return user;
    }

    public User getUserById(int id) {
        for (User user: users){
            if (user.getId() == id){
                return user;
            }
        }
        throw new IllegalArgumentException("User not found with id: "+ id);
    }

    public User getUserByUsername(String username) {
        for (User user: users){
            if (user.getUsername().equals(username)){
                return user;
            }
        }
        throw new IllegalArgumentException("User not found with username: "+ username);
    }

    public List<User> getAllUsers(){
        return Collections.unmodifiableList(new ArrayList<>(users));
    }

    public List<Integer> addFavoriteMovie(String username, int movieId) {
        User user = getUserByUsername(username);
        boolean isMovieExist = movieService.isMovieExist(movieId);
        if (isMovieExist) {
            user.getFavoriteMoviesId().add(movieId);
        }
        else {
            throw new IllegalArgumentException("Movie not found with id: " + movieId);
        }

        boolean isFavoriteMovieExist = user.getFavoriteMoviesId().stream().anyMatch(id -> id == movieId);
        if (!isFavoriteMovieExist) {
            user.getFavoriteMoviesId().add(movieId);
        } else {
            throw new IllegalArgumentException("Movie with id " + movieId + " is already in favorite list of user " + username);
        }
        return user.getFavoriteMoviesId();
    }
}
