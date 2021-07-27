package com.epam.jwd.web.command;

import com.epam.jwd.web.model.Movie;
import com.epam.jwd.web.model.Review;
import com.epam.jwd.web.model.User;
import com.epam.jwd.web.service.FilmService;
import com.epam.jwd.web.service.ReviewService;
import com.epam.jwd.web.service.UserService;

import javax.servlet.http.HttpSession;
import java.nio.charset.StandardCharsets;

public class ReviewCommand implements Command {

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
        String text = new String(request.getParameter("text").getBytes(StandardCharsets.ISO_8859_1), StandardCharsets.UTF_8);
        Integer value = Integer.valueOf(request.getParameter("value"));
        final String filmName = new String(request.getParameter("film").getBytes(StandardCharsets.ISO_8859_1), StandardCharsets.UTF_8);

        final HttpSession session = request.getCurrentSession().get();
        String name = (String) session.getAttribute("user");

        User user = userService.findByLogin(name);
        Movie movie = filmService.findByName(filmName);

        if (reviewService.findBy(movie, user) == null) {
            reviewService.create(new Review(user.getId(), movie.getId(), value, text));
            return new CommandResponse() {
                @Override
                public String getPath() {
                    return "/KinoLike/index.jsp";
                }

                @Override
                public boolean isRedirect() {
                    return true;
                }
            };
        } else {
            request.setAttribute("error", "Вы уже оставили отзыв на этот фильм!!");
            return new CommandResponse() {
                @Override
                public String getPath() {
                    return "/WEB-INF/jsp/review.jsp";
                }

                @Override
                public boolean isRedirect() {
                    return false;
                }
            };
        }
    }
}


