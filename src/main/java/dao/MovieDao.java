package dao;

import model.FilmGenre;
import model.Movie;
import model.User;

import java.util.List;
import java.util.Optional;

public interface MovieDao extends Dao<Movie>{

    Optional<Movie> findMovieByName(String name);

    List<Movie> findMoviesByGenre(FilmGenre genre);
}
