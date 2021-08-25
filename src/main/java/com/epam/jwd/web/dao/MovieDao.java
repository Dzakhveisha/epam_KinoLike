package com.epam.jwd.web.dao;

import com.epam.jwd.web.model.FilmGenre;
import com.epam.jwd.web.model.Movie;

import java.util.List;
import java.util.Optional;

/**
 * DAO interface connect with database,
 * do CRUD actions,
 * do specific for Movie actions
 */
public interface MovieDao extends Dao<Movie>{

    /**
     * find Movie by name in database
     *
     * @param name name of movie to be find
     * @return found movie
     */
    Optional<Movie> findMovieByName(String name);

    /**
     * find all Movies by genre in database
     *
     * @param genre genre of movies to be find
     * @return all movies with such genre
     */
    List<Movie> findMoviesByGenre(FilmGenre genre);

    /**
     * find all movies ordered by release year in database
     *
     * @return all movies ordered by release year
     */
    List<Movie> findAllByNew();

    /**
     * find all movies ordered by it's rating in database
     *
     * @return all movies ordered by rating
     */
    List<Movie> findAllByPopular();

    /**
     * find all movies, which contains string in name, in database
     *
     * @param searchStr string to be contained by movie name
     * @return all movies with string in name
     */
    List<Movie> findAllByString(String searchStr);
}
