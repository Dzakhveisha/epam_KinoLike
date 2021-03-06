package com.epam.jwd.web.service;

import com.epam.jwd.web.dao.MovieDao;
import com.epam.jwd.web.dao.DaoImpl.JdbcGenreDao;
import com.epam.jwd.web.dao.DaoImpl.JdbcMovieDao;
import com.epam.jwd.web.exception.UnknownEntityException;
import com.epam.jwd.web.model.FilmGenre;
import com.epam.jwd.web.model.Movie;

import java.util.Collections;
import java.util.List;

public class FilmService {

    private static final String NOT_FOUND_WITH_NAME_MSG = "Film with such name does not exist!";
    private static final String NOT_FOUND_WITH_ID_MSG = "Film with such id does not exist!";


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


    public List<FilmGenre> findAllGenres(){
        return JdbcGenreDao.getInstance().findAll();
    }

    public List<Movie> finAllByGenre(FilmGenre genre){
        return movieDao.findMoviesByGenre(genre);
    }

    public Movie findByName(String name){
        return movieDao.findMovieByName(name).orElseThrow(() -> new UnknownEntityException(NOT_FOUND_WITH_NAME_MSG));
    }

    public Movie findById(Long id){
        return movieDao.findById(id).orElseThrow(() -> new UnknownEntityException(NOT_FOUND_WITH_ID_MSG));
    }


    public Movie create(Movie movie){
        return movieDao.save(movie);
    }

    public void deleteByName(String name) throws UnknownEntityException{
            Movie movie = findByName(name);
            movieDao.delete(movie.getId());
    }

    public Movie update(Movie movie){
        return movieDao.update(movie);
    }

    public List<Movie> findAllByString(String searchStr) {
        return movieDao.findAllByString(searchStr);
    }
}

