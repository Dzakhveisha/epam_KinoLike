package com.epam.jwd.web.dao;

import com.epam.jwd.web.model.FilmGenre;
import com.epam.jwd.web.model.Movie;

import java.util.List;
import java.util.Optional;

public interface MovieDao extends Dao<Movie>{

    Optional<Movie> findMovieByName(String name);

    List<Movie> findMoviesByGenre(FilmGenre genre);

    List<Movie> findAllByNew();

    List<Movie> findAllByPopular();
}
