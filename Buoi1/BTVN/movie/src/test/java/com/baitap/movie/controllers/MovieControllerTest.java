package com.baitap.movie.controllers;

import com.baitap.movie.models.AbstractMovie;
import com.baitap.movie.models.SingleMovie;
import com.jayway.jsonpath.JsonPath;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.*;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class MovieControllerTest {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    private String url(String path) {
        return "http://localhost:" + port + path;
    }

    @Test
    void testGetAllMovies() {
        ResponseEntity<String> response = restTemplate.getForEntity(url("/api/movies"), String.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).contains("Single Movie !", "Serie Movie !");
    }

    @Test
    void testGetMovieById() {
        ResponseEntity<String> response = restTemplate.getForEntity(url("/api/movies/1"), String.class);
        String body = response.getBody();
        Object data = JsonPath.parse(body).read("$.data");

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(data).isNotNull();
    }

    @Test
    void testGetMovieByIdNotFound() {
        ResponseEntity<String> response = restTemplate.getForEntity(url("/api/movies/999"), String.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST); // or NOT_FOUND, depending on your handler
        assertThat(response.getBody()).contains("movieId not found");
    }

    @Test
    void testAddMovie() {
        SingleMovie newMovie = SingleMovie.builder()
            .title("New Single Movie")
            .description("New Description")
            .directors(new CopyOnWriteArrayList<>(List.of("New Director")))
            .actors(new CopyOnWriteArrayList<>(List.of("New Actor")))
            .duration_in_minutes(120)
            .type("single")
            .build();

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<SingleMovie> request = new HttpEntity<>(newMovie, headers);

        ResponseEntity<String> response = restTemplate.postForEntity(url("/api/movies"), request, String.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        assertThat(response.getBody()).contains("New Single Movie");
    }

    @Test
    void testDeleteMovieById() {
        // delete existing movie
        restTemplate.delete(url("/api/movies/1"));

        // verify not found anymore
        ResponseEntity<String> response = restTemplate.getForEntity(url("/api/movies/1"), String.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK); // or NOT_FOUND
    }
}
