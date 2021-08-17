package com.epam.jwd.web.command;

import com.epam.jwd.web.Validator.Validator;
import com.epam.jwd.web.exception.DataIsNotValidateException;
import com.epam.jwd.web.exception.UnknownEntityException;
import com.epam.jwd.web.model.Movie;
import com.epam.jwd.web.model.Review;
import com.epam.jwd.web.model.User;
import com.epam.jwd.web.service.FilmService;
import com.epam.jwd.web.service.ReviewService;
import com.epam.jwd.web.service.UserService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpSession;
import java.nio.charset.StandardCharsets;

public class ReviewCommand implements Command {
    static final Logger LOGGER = LogManager.getRootLogger();
    private static final String TEXT_PARAMETER = "text";
    private static final String VALUE_PARAMETER = "value";
    private static final String FILM_PARAMETER = "film";
    private static final String USER_ATTRIBUTE = "user";
    private static final String ERROR_ATTRIBUTE = "error";
    private static final String REVIEW_IS_EXIST_MSG = "You have already left a review for this movie !";
    private static final String NOT_ENOUGH_DATA_MSG = "Not enough data!";

    private final FilmService filmService;
    private final UserService userService;
    private final ReviewService reviewService;
    private final Validator dataValidator;

    public ReviewCommand() {
        filmService = FilmService.getInstance();
        userService = UserService.getInstance();
        reviewService = ReviewService.getInstance();
        dataValidator = Validator.getInstance();
    }

    @Override
    public CommandResponse execute(CommandRequest request) {

        String text = request.getParameter(TEXT_PARAMETER);
        String valueStr = request.getParameter(VALUE_PARAMETER);
        String filmName = request.getParameter(FILM_PARAMETER);

        if (text == null || valueStr == null || filmName == null){
            request.setAttribute(ERROR_ATTRIBUTE, NOT_ENOUGH_DATA_MSG);
            return new SimpleCommandResponse("/WEB-INF/jsp/error.jsp", false);
        }

        text = replaceScripts(new String(text.getBytes(StandardCharsets.ISO_8859_1), StandardCharsets.UTF_8));
        Integer value = Integer.valueOf(valueStr);
        filmName = replaceScripts(new String(filmName.getBytes(StandardCharsets.ISO_8859_1), StandardCharsets.UTF_8));

        final HttpSession session = request.getCurrentSession().get();
        String name = (String) session.getAttribute(USER_ATTRIBUTE);
        User user = userService.findByLogin(name);
        Movie movie;

        try {
            movie = filmService.findByName(filmName);
        }catch (UnknownEntityException e){
            LOGGER.error(e.getMessage() + " Name:" + filmName);
            request.setAttribute(ERROR_ATTRIBUTE, e.getMessage());
            return new SimpleCommandResponse("/WEB-INF/jsp/error.jsp", false);
        }

        Review newReview = new Review(user.getId(), movie.getId(), value, text);
        try{
            dataValidator.validateReview(newReview);
        } catch (DataIsNotValidateException e) {
            LOGGER.error(e.getMessage());
            request.setAttribute(ERROR_ATTRIBUTE, e.getMessage());
            return new SimpleCommandResponse("/WEB-INF/jsp/error.jsp", false);
        }

        try {
            reviewService.findBy(movie, user);
        } catch (UnknownEntityException e){
            LOGGER.error(e.getMessage()+ " User: " + user.getName() + " Movie: " + movie.getName());
            reviewService.create(newReview);
            return new SimpleCommandResponse("/KinoLike/index.jsp", true);
        }
        request.setAttribute(ERROR_ATTRIBUTE, REVIEW_IS_EXIST_MSG);
        return new SimpleCommandResponse("/WEB-INF/jsp/error.jsp", false);
    }

    private String replaceScripts(String string) {
        string = string.replaceAll("<", "&lt");
        string = string.replaceAll(">", "&gt");
        return string;
    }
}


