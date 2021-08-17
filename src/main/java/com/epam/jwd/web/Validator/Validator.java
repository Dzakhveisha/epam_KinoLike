package com.epam.jwd.web.Validator;

import com.epam.jwd.web.exception.DataIsNotValidateException;
import com.epam.jwd.web.model.Movie;
import com.epam.jwd.web.model.Review;
import com.epam.jwd.web.model.User;

public class Validator {

    private static Validator instance;

    public static Validator getInstance() {
        Validator localInstance = instance;
        if (localInstance == null) {
            synchronized (Validator.class) {
                localInstance = instance;
                if (localInstance == null) {
                    localInstance = new Validator();
                    instance = localInstance;
                }
            }
        }
        return localInstance;
    }

    private Validator() {
    }

    public void validateUser(User user) throws DataIsNotValidateException {
        if (user.getName().length() > 40 || user.getPasswordHash().length() > 40){
            throw new DataIsNotValidateException("Login or password is too long!");
        }
        if (user.getAge() > 110 && user.getAge() < 6){
            throw new DataIsNotValidateException("Age is not real!");
        }
        if (!user.getEmail().contains("@")){
            throw new DataIsNotValidateException("Email is not correct!");
        }
    }

    public void validateFilm(Movie movie) throws DataIsNotValidateException{
        if(movie.getYear() < 1895 || movie.getYear() > 2022) {
            throw new DataIsNotValidateException("Year is not Real!");
        }
        if(movie.getName().length() > 45) {
            throw new DataIsNotValidateException("Film name is too long!");
        }
        if(movie.getCountryName().length() > 50) {
            throw new DataIsNotValidateException("Country name is too long!");
        }
        if(movie.getDescription().length() > 2000) {
            throw new DataIsNotValidateException("Film description is too long!");
        }
    }

    public void validateReview(Review review) throws DataIsNotValidateException{
        if (review.getValue() > 10 || review.getValue() < 0){
            throw new DataIsNotValidateException("Rating is not Real! It must be between 0 and 10.");
        }
        if (review.getText().length() > 1000){
            throw new DataIsNotValidateException("Text comment is too long!");
        }
    }
}
