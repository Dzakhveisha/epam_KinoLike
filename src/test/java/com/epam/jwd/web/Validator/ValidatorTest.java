package com.epam.jwd.web.Validator;

import com.epam.jwd.web.exception.DataIsNotValidateException;
import com.epam.jwd.web.model.FilmGenre;
import com.epam.jwd.web.model.Movie;
import com.epam.jwd.web.model.Review;
import com.epam.jwd.web.model.User;
import org.junit.jupiter.api.Test;

import static org.junit.Assert.assertThrows;

public class ValidatorTest {

    private static final String VALID_NAME = "Jack_q";
    private static final String VALID_PASSWORD = "qwerty123456";
    private static final String VALID_MAIL = "Jack@mail.ru";
    private static final Integer VALID_AGE = 24;
    private static final String INVALID_NAME = "Jack_qQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQ";
    private static final String INVALID_PASSWORD = "qwerty1234567777777777777777777777777777777777777777777777777777777";
    private static final String INVALID_MAIL = "Jack.ru";
    private static final Integer INVALID_AGE = -56;

    private static final Integer VALID_REVIEW_VALUE = 8;
    private static final String VALID_REVIEW_TEXT = "COOL! AMUSING!";
    private static final Integer INVALID_REVIEW_VALUE = 12;

    private static final Integer VALID_FILM_YEAR = 1996;
    private static final String VALID_FILM_NAME = "FILM NAME";
    private static final String VALID_FILM_DESCRIPTION = "FILM DESCRIPTION";
    private static final Integer VALID_FILM_COUNTRY_ID = 1;
    private static final Integer INVALID_FILM_YEAR = 1302;
    private static final String INVALID_FILM_NAME = "FILMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMM NAME";
    private static final FilmGenre VALID_FILM_GENRE = FilmGenre.ADVENTURE;
    private static final String VALID_FILM_IMAGE_PATH = "";

    @Test
    public void validateValidUserTest() throws DataIsNotValidateException {
        User user = new User(VALID_NAME, VALID_PASSWORD, VALID_MAIL, VALID_AGE);
        Validator.getInstance().validateUser(user);
    }

    @Test
    public void validateInvalidMailUserTest() throws DataIsNotValidateException {
        User user = new User(VALID_NAME, VALID_PASSWORD, INVALID_MAIL, VALID_AGE);
        Throwable exception = assertThrows(DataIsNotValidateException.class,
                () -> {
                    Validator.getInstance().validateUser(user);
                });
    }

    @Test
    public void validateInvalidNameUserTest() throws DataIsNotValidateException {
        User user = new User(INVALID_NAME, VALID_PASSWORD, VALID_MAIL, VALID_AGE);
        Throwable exception = assertThrows(DataIsNotValidateException.class,
                () -> {
                    Validator.getInstance().validateUser(user);
                });
    }

    @Test
    public void validateInvalidPasswordUserTest() throws DataIsNotValidateException {
        User user = new User(VALID_NAME, INVALID_PASSWORD, VALID_MAIL, VALID_AGE);
        Throwable exception = assertThrows(DataIsNotValidateException.class,
                () -> {
                    Validator.getInstance().validateUser(user);
                });
    }

    @Test
    public void validateInvalidAgeUserTest() throws DataIsNotValidateException {
        User user = new User(VALID_NAME, VALID_PASSWORD, VALID_MAIL, INVALID_AGE);
        Throwable exception = assertThrows(DataIsNotValidateException.class,
                () -> {
                    Validator.getInstance().validateUser(user);
                });
    }

    @Test
    public void validateValidFilm() throws DataIsNotValidateException {
        Movie movie = new Movie(VALID_FILM_NAME, VALID_FILM_YEAR, VALID_FILM_DESCRIPTION,
                VALID_FILM_COUNTRY_ID, VALID_FILM_GENRE.getId(), VALID_FILM_IMAGE_PATH);
        Validator.getInstance().validateFilm(movie);

    }

    @Test
    public void validateInvalidYearFilm() throws DataIsNotValidateException {
        Movie movie = new Movie(VALID_FILM_NAME, INVALID_FILM_YEAR, VALID_FILM_DESCRIPTION,
                VALID_FILM_COUNTRY_ID, VALID_FILM_GENRE.getId(), VALID_FILM_IMAGE_PATH);
        Throwable exception = assertThrows(DataIsNotValidateException.class,
                () -> {
                    Validator.getInstance().validateFilm(movie);
                });
    }

    @Test
    public void validateInvalidNameFilm() throws DataIsNotValidateException {
        Movie movie = new Movie(INVALID_FILM_NAME, VALID_FILM_YEAR, VALID_FILM_DESCRIPTION,
                VALID_FILM_COUNTRY_ID, VALID_FILM_GENRE.getId(), VALID_FILM_IMAGE_PATH);
        Throwable exception = assertThrows(DataIsNotValidateException.class,
                () -> {
                    Validator.getInstance().validateFilm(movie);
                });
    }

    @Test
    public void validateValidReview() throws DataIsNotValidateException {
        Review review = new Review(0, 0, VALID_REVIEW_VALUE, VALID_REVIEW_TEXT);
        Validator.getInstance().validateReview(review);
    }

    @Test
    public void validateInvalidValueReview() throws DataIsNotValidateException {
        Review review = new Review(0, 0, INVALID_REVIEW_VALUE, VALID_REVIEW_TEXT);
        Throwable exception = assertThrows(DataIsNotValidateException.class,
                () -> {
                    Validator.getInstance().validateReview(review);
                });
    }
}