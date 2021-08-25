package com.epam.jwd.web.dao;

import com.epam.jwd.web.model.Movie;
import com.epam.jwd.web.model.Review;
import com.epam.jwd.web.model.User;

import java.util.List;
import java.util.Optional;

/**
 * DAO interface connect with database,
 * do CRUD actions,
 * do specific for Review actions
 */
public interface ReviewDao extends  Dao<Review>{

    /**
     * find review by film and movie in database
     *
     * @param movie movie that was given a review
     * @param user user, who given review
     * @return found review
     */
    Optional<Review> findByFilmAndUser(Movie movie, User user);

    /**
     * find all the reviews that the user gave in database
     *
     * @param user user, who given reviews
     * @return all reviews
     */
    List<Review> findAllByUser(User user);
}
