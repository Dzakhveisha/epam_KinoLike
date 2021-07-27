package com.epam.jwd.web;

import com.epam.jwd.web.dao.ReviewDao;
import com.epam.jwd.web.dao.impl.JdbcReviewDao;
import com.epam.jwd.web.model.Review;
import com.epam.jwd.web.service.FilmService;
import com.epam.jwd.web.service.UserService;

public class Main {
    public static void main(String[] args) {
        final UserService userService = UserService.getInstance();
        final FilmService filmService = FilmService.getInstance();

        ReviewDao dao = JdbcReviewDao.getInstance();
        Review review = dao.findByFilmAndUser(filmService.findAll().get(1),
                userService.findByLogin("Dasha")).orElse(null);
    }
}
