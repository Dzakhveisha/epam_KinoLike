package com.epam.jwd.web.command.page;

import com.epam.jwd.web.command.Command;
import com.epam.jwd.web.command.CommandRequest;
import com.epam.jwd.web.command.CommandResponse;
import com.epam.jwd.web.model.FilmGenre;
import com.epam.jwd.web.model.Movie;
import com.epam.jwd.web.model.User;
import com.epam.jwd.web.model.UserStatus;
import com.epam.jwd.web.service.FilmService;
import com.epam.jwd.web.service.UserService;

import javax.servlet.http.HttpSession;
import java.util.List;

public class ShowProfilePageCommand implements Command {

    private static final String FILMS_ATTRIBUTE = "films";
    private static final String USER_ATTRIBUTE = "user";

    private final FilmService FILM_SERVICE;
    private final UserService userService;

    public ShowProfilePageCommand() {
        FILM_SERVICE = FilmService.getInstance();
        userService = UserService.getInstance();
    }

    @Override
    public CommandResponse execute(CommandRequest request) {
        List<Movie> films = FILM_SERVICE.findLikedFilms();
        request.setAttribute(FILMS_ATTRIBUTE, films);

        final HttpSession session = request.getCurrentSession().get();
        String name = (String) session.getAttribute("user");
        User curUser = userService.findByLogin(name);
        request.setAttribute(USER_ATTRIBUTE, curUser);
        return new CommandResponse() {
            @Override
            public String getPath() {
                return "/WEB-INF/jsp/profile.jsp";
            }

            @Override
            public boolean isRedirect() {
                return false;
            }
        };
    }
}
