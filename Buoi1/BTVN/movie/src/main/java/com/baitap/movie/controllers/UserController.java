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

import com.baitap.movie.dto.UserDTO;
import com.baitap.movie.dto.UserFavoriteRequestDTO;
import com.baitap.movie.models.User;
import com.baitap.movie.responses.ApiResponse;
import com.baitap.movie.responses.ResponseHandler;
import com.baitap.movie.services.UserService;

import jakarta.validation.Valid;

@RestController
public class UserController {
    private final UserService userService;
    
    @Autowired
    public UserController(UserService userService){
        this.userService = userService;
    }

    @GetMapping("/api/users/{userId}")
    public ResponseEntity<ApiResponse<User>> getUserById(@PathVariable int userId) {
        User user = userService.getUserById(userId);
        return ResponseHandler.generateResponse(HttpStatus.OK.value(), null, user);
    }

    @PostMapping("/api/users")
    public ResponseEntity<ApiResponse<User>> addUser(@RequestBody UserDTO request) {
        User user = User.builder()
                        .username(request.getUsername())
                        .favoriteMoviesId(request.getFavoriteMoviesId() != null ? 
                                          new CopyOnWriteArrayList<>(request.getFavoriteMoviesId()) : 
                                          new CopyOnWriteArrayList<>())
                        .build();
        user = userService.addUser(user);
        return ResponseHandler.generateResponse(HttpStatus.CREATED.value(), null, user);
    }

    @PostMapping("/api/users/favorite")
    public ResponseEntity<ApiResponse<List<Integer>>> addFavoriteMovie(@RequestBody @Valid UserFavoriteRequestDTO request) {
        List<Integer> favoriteMovies = userService.addFavoriteMovie(request.getUsername(), request.getMovieId());
        return ResponseHandler.generateResponse(HttpStatus.ACCEPTED.value(),
            "Favorite movie added successfully",
            favoriteMovies);
    }
    

}
