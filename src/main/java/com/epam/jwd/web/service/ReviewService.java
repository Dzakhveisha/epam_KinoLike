package com.epam.jwd.web.service;

import com.epam.jwd.web.dao.ReviewDao;
import com.epam.jwd.web.dao.DaoImpl.JdbcReviewDao;
import com.epam.jwd.web.exception.UnknownEntityException;
import com.epam.jwd.web.model.Movie;
import com.epam.jwd.web.model.Review;
import com.epam.jwd.web.model.User;

import java.util.List;

public class ReviewService {
    private static final String REVIEW_NOT_FOUND_MSG = " Review for this user and this movie not found!";
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
        return reviewDao.findByFilmAndUser(movie, user).orElseThrow(() -> new UnknownEntityException(REVIEW_NOT_FOUND_MSG));
    }

    public Review create(Review review) {
        return  reviewDao.save(review);
    }

}
