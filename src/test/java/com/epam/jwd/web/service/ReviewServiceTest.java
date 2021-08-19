package com.epam.jwd.web.service;

import com.epam.jwd.web.dao.DaoImpl.JdbcReviewDao;
import com.epam.jwd.web.dao.ReviewDao;
import com.epam.jwd.web.exception.UnknownEntityException;
import com.epam.jwd.web.model.Movie;
import com.epam.jwd.web.model.User;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeAll;

import java.util.Optional;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class ReviewServiceTest {

    private static ReviewDao reviewDao;
    private static final String REVIEW_NOT_FOUND_MSG = " Review for this user and this movie not found!";


    @BeforeAll
    public static void setUp(){
        reviewDao = mock(JdbcReviewDao.class);
    }


    @Test
    public void CuntFindByMovieAndUserTest() {
        Movie movie = new Movie("film", 2002,"film description", 1, 2L,"img.img");
        User user = new User(1, "Dasha", 19, 2, "dasha@mail", "ghj222", 2);

        when(reviewDao.findByFilmAndUser(movie, user)).thenReturn(Optional.empty());
        Throwable exception = assertThrows(UnknownEntityException.class,
                ()->{ReviewService.getInstance().findBy(movie, user);});
        assertEquals(REVIEW_NOT_FOUND_MSG, exception.getMessage());
    }
}