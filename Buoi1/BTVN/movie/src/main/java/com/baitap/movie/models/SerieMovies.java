package com.baitap.movie.models;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.SuperBuilder;

@Data
@EqualsAndHashCode(callSuper = true)
@SuperBuilder
public class SerieMovies extends AbstractMovie {
    private int number_of_seasons;
    private int number_of_episodes;
}
