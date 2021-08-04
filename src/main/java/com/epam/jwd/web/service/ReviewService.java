package com.epam.jwd.web.service;

import com.epam.jwd.web.dao.MovieDao;
import com.epam.jwd.web.dao.ReviewDao;
import com.epam.jwd.web.dao.impl.JdbcGenreDao;
import com.epam.jwd.web.dao.impl.JdbcMovieDao;
import com.epam.jwd.web.dao.impl.JdbcReviewDao;
import com.epam.jwd.web.exception.UnknownEntityException;
import com.epam.jwd.web.model.FilmGenre;
import com.epam.jwd.web.model.Movie;
import com.epam.jwd.web.model.Review;
import com.epam.jwd.web.model.User;

import java.util.Collections;
import java.util.List;

public class ReviewService {
    private static ReviewService instance;
    private final ReviewDao reviewDao;

    private ReviewService() {
        reviewDao = JdbcReviewDao.getInstance();
    }

    public static ReviewService getInstance() {
        ReviewService localInstance = instance;
        if (localInstance == null) {
            synchronized (ReviewService.class) {
                localInstance = instance;
                if (localInstance == null) {
                    localInstance = new ReviewService();
                    instance = localInstance;
                }
            }
        }
        return localInstance;
    }

    public List<Review> findAll() {
        return reviewDao.findAll();
    }

    public List<Review> findAllByUser(User user) {
        return reviewDao.findAllByUser(user);
    }

    public Review findBy(Movie movie, User user) throws UnknownEntityException {
        return reviewDao.findByFilmAndUser(movie, user).orElseThrow(() -> new UnknownEntityException(" Review for this user and this movie not faund!"));
    }

    public Review create(Review review) {
        return  reviewDao.save(review);
    }

}
