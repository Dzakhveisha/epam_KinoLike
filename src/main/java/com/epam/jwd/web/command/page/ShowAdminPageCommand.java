package com.epam.jwd.web.command.page;

import com.epam.jwd.web.command.Command;
import com.epam.jwd.web.command.CommandRequest;
import com.epam.jwd.web.command.CommandResponse;
import com.epam.jwd.web.command.SimpleCommandResponse;
import com.epam.jwd.web.model.FilmGenre;
import com.epam.jwd.web.model.Movie;
import com.epam.jwd.web.model.User;
import com.epam.jwd.web.model.UserStatus;
import com.epam.jwd.web.service.FilmService;
import com.epam.jwd.web.service.UserService;

import javax.servlet.http.HttpSession;
import java.util.List;

public class ShowAdminPageCommand implements Command {

    private static final String FILMS_ATTRIBUTE = "films";
    private static final String USERS_ATTRIBUTE = "users";
    private static final String ADMIN_ATTRIBUTE = "admin";
    private static final String STATUSES_ATTRIBUTE = "statuses";
    private static final String GENRES_ATTRIBUTE = "genres";
    private static final String USER_ATTRIBUTE = "user";
    private final UserService userService;
    private final FilmService filmService;

    public ShowAdminPageCommand() {
        filmService = FilmService.getInstance();
        userService = UserService.getInstance();
    }

    @Override
    public CommandResponse execute(CommandRequest request) {
        final HttpSession session = request.getCurrentSession().get();
        String name = (String) session.getAttribute(USER_ATTRIBUTE);
        User curUser = userService.findByLogin(name);
        List<Movie> films = filmService.findAll();
        List<User> users = userService.findAll();
        List<UserStatus> statuses = userService.findAllStatuses();
        List<FilmGenre> genres = filmService.findAllGenres();

        request.setAttribute(GENRES_ATTRIBUTE, genres);
        request.setAttribute(STATUSES_ATTRIBUTE, statuses);
        request.setAttribute(FILMS_ATTRIBUTE, films);
        request.setAttribute(USERS_ATTRIBUTE, users);
        request.setAttribute(ADMIN_ATTRIBUTE, curUser);
        return new SimpleCommandResponse("/WEB-INF/jsp/admin.jsp", false);
    }
}
