package com.epam.jwd.web.command;

import com.epam.jwd.web.exception.UnknownEntityException;
import com.epam.jwd.web.model.Movie;
import com.epam.jwd.web.model.Review;
import com.epam.jwd.web.model.User;
import com.epam.jwd.web.service.FilmService;
import com.epam.jwd.web.service.ReviewService;
import com.epam.jwd.web.service.UserService;

import javax.servlet.http.HttpSession;
import java.nio.charset.StandardCharsets;

public class ReviewCommand implements Command {

    private static final String TEXT_PARAMETER = "text";
    private static final String VALUE_PARAMETER = "value";
    private static final String FILM_PARAMETER = "film";
    private static final String USER_ATTRIBUTE = "user";
    private final FilmService filmService;
    private final UserService userService;
    private final ReviewService reviewService;

    public ReviewCommand() {
        filmService = FilmService.getInstance();
        userService = UserService.getInstance();
        reviewService = ReviewService.getInstance();
    }

    @Override
    public CommandResponse execute(CommandRequest request) {
        String text = new String(request.getParameter(TEXT_PARAMETER).getBytes(StandardCharsets.ISO_8859_1), StandardCharsets.UTF_8);
        Integer value = Integer.valueOf(request.getParameter(VALUE_PARAMETER));
        final String filmName = new String(request.getParameter(FILM_PARAMETER).getBytes(StandardCharsets.ISO_8859_1), StandardCharsets.UTF_8);

        final HttpSession session = request.getCurrentSession().get();
        String name = (String) session.getAttribute(USER_ATTRIBUTE);
        User user = userService.findByLogin(name);
        Movie movie;

        try {
            movie = filmService.findByName(filmName);
        }catch (UnknownEntityException e){
            //todo log
            request.setAttribute("error", "Нет такого фильма!!");
            return new SimpleCommandResponse("/WEB-INF/jsp/review.jsp", false);
        }

        try {
            reviewService.findBy(movie, user);
        } catch (UnknownEntityException e){
            reviewService.create(new Review(user.getId(), movie.getId(), value, text));
            return new SimpleCommandResponse("/KinoLike/index.jsp", true);
        }
        request.setAttribute("error", "Вы уже оставили отзыв на этот фильм!!");
        return new SimpleCommandResponse("/WEB-INF/jsp/review.jsp", false);
    }
}


