package com.epam.jwd.web.dao;

import com.epam.jwd.web.model.Movie;
import com.epam.jwd.web.model.Review;
import com.epam.jwd.web.model.User;

import java.util.Optional;

public interface ReviewDao extends  Dao<Review>{

    Optional<Review> findByFilmAndUser(Movie movie, User user);
}
