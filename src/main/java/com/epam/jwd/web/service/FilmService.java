package com.epam.jwd.web.service;

import com.epam.jwd.web.dao.Dao;
import com.epam.jwd.web.dao.MovieDao;
import com.epam.jwd.web.dao.impl.JdbcGenreDao;
import com.epam.jwd.web.dao.impl.JdbcMovieDao;
import com.epam.jwd.web.model.FilmGenre;
import com.epam.jwd.web.model.Movie;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

public class FilmService {

    private static FilmService instance;
    private final MovieDao movieDao;
    private final JdbcGenreDao genreDao;

    private FilmService() {
        movieDao = JdbcMovieDao.getInstance();
        genreDao = JdbcGenreDao.getInstance();
    }

    public static FilmService getInstance() {
        FilmService localInstance = instance;
        if (localInstance == null) {
            synchronized (FilmService.class) {
                localInstance = instance;
                if (localInstance == null) {
                    localInstance = new FilmService();
                    instance = localInstance;
                }
            }
        }
        return localInstance;
    }

    public List<Movie> findAll() {
        return movieDao.findAll();
    }

    public List<Movie> findAllNew() {
        return movieDao.findAllByNew();
    }

    public List<Movie> findAllPopular() {
        return movieDao.findAllByPopular();
    }

    public List<Movie> findLikedFilms() {
        return Collections.emptyList(); //todo
    }

    public List<FilmGenre> findAllGenres(){
        return JdbcGenreDao.getInstance().findAll();
    }

    public List<Movie> finAllByGenre(FilmGenre genre){
        return movieDao.findMoviesByGenre(genre);
    }

    public Movie findByName(String name){
        return movieDao.findMovieByName(name).orElse(null);
    }

    public Movie create(Movie movie){
        return movieDao.save(movie);
    }
}

