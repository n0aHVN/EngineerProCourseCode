package com.baitap.movie.controllers;

import com.baitap.movie.dto.UserDTO;
import com.baitap.movie.dto.UserFavoriteRequestDTO;
import com.baitap.movie.models.User;
import com.baitap.movie.services.UserService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(UserController.class)
public class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private UserService userService;

    @Test
    void testGetUserById() throws Exception {
        User user = User.builder().username("testUser").build();
        when(userService.getUserById(1)).thenReturn(user);

        mockMvc.perform(get("/api/users/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.username").value("testUser"));
    }

    @Test
    void testAddUser() throws Exception {
        UserDTO userDTO = new UserDTO();
        userDTO.setUsername("newUser");
        userDTO.setFavoriteMoviesId(List.of(1, 2));

        User user = User.builder().username("newUser").build();
        when(userService.addUser(Mockito.any(User.class))).thenReturn(user);

        mockMvc.perform(post("/api/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"username\":\"newUser\",\"favoriteMoviesId\":[1,2]}"))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.data.username").value("newUser"));
    }

    @Test
    void testAddFavoriteMovie() throws Exception {
        UserFavoriteRequestDTO requestDTO = new UserFavoriteRequestDTO();
        requestDTO.setUsername("testUser");
        requestDTO.setMovieId(1);

        when(userService.addFavoriteMovie("testUser", 1)).thenReturn(List.of(1));

        mockMvc.perform(post("/api/users/favorite")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"username\":\"testUser\",\"movieId\":1}"))
                .andExpect(status().isAccepted())
                .andExpect(jsonPath("$.data[0]").value(1));
    }
}