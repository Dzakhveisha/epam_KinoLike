package com.epam.jwd.web.command.page;

import com.epam.jwd.web.command.Command;
import com.epam.jwd.web.command.CommandRequest;
import com.epam.jwd.web.command.CommandResponse;
import com.epam.jwd.web.command.SimpleCommandResponse;
import com.epam.jwd.web.model.*;
import com.epam.jwd.web.service.FilmService;
import com.epam.jwd.web.service.ReviewService;
import com.epam.jwd.web.service.UserService;

import javax.servlet.http.HttpSession;
import java.util.*;

public class ShowProfilePageCommand implements Command {

    private static final String REVIEWS_ATTRIBUTE = "reviews";
    private static final String FILMS_ATTRIBUTE = "films";
    private static final String USER_ATTRIBUTE = "user";

    private final UserService userService;
    private final ReviewService reviewService;
    private final FilmService filmService;

    public ShowProfilePageCommand() {
        userService = UserService.getInstance();
        reviewService = ReviewService.getInstance();
        filmService = FilmService.getInstance();
    }

    @Override
    public CommandResponse execute(CommandRequest request) {
        final HttpSession session = request.getCurrentSession().get();
        String name = (String) session.getAttribute(USER_ATTRIBUTE);
        User curUser = userService.findByLogin(name);
        request.setAttribute(USER_ATTRIBUTE, curUser);

        List<Review> reviews = reviewService.findAllByUser(curUser);

        Map<Movie, Review> reviewMovieMap = new HashMap<>();
        List<Movie> films = new ArrayList<>();
        for (Review review: reviews) {
            Movie film = filmService.findById(review.getFilmId());
            reviewMovieMap.put(film,review);
            films.add(film);
        };
        request.setAttribute(REVIEWS_ATTRIBUTE, reviewMovieMap);
        request.setAttribute(FILMS_ATTRIBUTE, films);

        return new SimpleCommandResponse("/WEB-INF/jsp/profile.jsp", false);

    }
}
