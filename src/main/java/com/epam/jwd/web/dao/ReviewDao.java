package com.epam.jwd.web.dao;

import com.epam.jwd.web.model.Movie;
import com.epam.jwd.web.model.Review;

import java.util.List;

public interface ReviewDao extends  Dao<Review>{

    List<Review> findByFilm(Movie movie);
}
