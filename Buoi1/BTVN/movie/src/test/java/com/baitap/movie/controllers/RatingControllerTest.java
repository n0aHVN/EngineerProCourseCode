package com.baitap.movie.controllers;

import com.baitap.movie.dto.RatingDTO;
import com.baitap.movie.models.Rating;
import com.baitap.movie.services.RatingService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;
@WebMvcTest(RatingController.class)
public class RatingControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private RatingService ratingService;

    @Test
    void testGetRatingByMovieId() throws Exception {
        Rating rating = Rating.builder().movieId(1).userId(1).rating(5).build();
        when(ratingService.getRatingsByMovieId(1)).thenReturn(List.of(rating));

        mockMvc.perform(get("/api/ratings/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data[0].movieId").value(1))
                .andExpect(jsonPath("$.data[0].userId").value(1))
                .andExpect(jsonPath("$.data[0].rating").value(5));
    }

    @Test
    void testAddRating() throws Exception {
        RatingDTO ratingDTO = new RatingDTO();
        ratingDTO.setUserId(1);
        ratingDTO.setMovieId(1);
        ratingDTO.setRating(5);

        Rating rating = Rating.builder().movieId(1).userId(1).rating(5).build();
        when(ratingService.addRating(Mockito.any(Rating.class))).thenReturn(rating);

        mockMvc.perform(post("/api/ratings")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"userId\":1,\"movieId\":1,\"rating\":5}"))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.data.movieId").value(1))
                .andExpect(jsonPath("$.data.userId").value(1))
                .andExpect(jsonPath("$.data.rating").value(5));
    }
    // Negative Test: Add Rating with Missing Fields
    @Test
    void testAddRatingWithMissingFields() throws Exception {
        MvcResult result = mockMvc.perform(post("/api/ratings")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"userId\":1}")) // Missing movieId and rating
                .andExpect(status().isBadRequest())
                .andReturn();
        String responseBody = result.getResponse().getContentAsString();
        System.out.println(responseBody);
    }

    // Negative Test: Add Rating with Invalid Data
    @Test
    void testAddRatingWithInvalidData() throws Exception {
        mockMvc.perform(post("/api/ratings")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"userId\":1,\"movieId\":1,\"rating\":-1}")) // Invalid rating
                .andExpect(status().isBadRequest())
                .andDo(print());
    }

    
}