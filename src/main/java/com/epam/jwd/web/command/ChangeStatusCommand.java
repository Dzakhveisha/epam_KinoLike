package com.epam.jwd.web.command;

import com.epam.jwd.web.model.FilmGenre;
import com.epam.jwd.web.model.Movie;
import com.epam.jwd.web.model.User;
import com.epam.jwd.web.model.UserStatus;
import com.epam.jwd.web.service.FilmService;
import com.epam.jwd.web.service.UserService;

import javax.servlet.http.HttpSession;
import java.util.List;

public class ChangeStatusCommand implements Command{
    private static final String FILMS_ATTRIBUTE = "films";
    private static final String USERS_ATTRIBUTE = "users";
    private static final String ADMIN_ATTRIBUTE = "admin";
    private static final String STATUSES_ATTRIBUTE = "statuses";
    private static final String GENRES_ATTRIBUTE = "genres";
    private final FilmService filmService;
    private final UserService userService;

    public ChangeStatusCommand() {
        filmService = FilmService.getInstance();
        userService = UserService.getInstance();
    }
    @Override
    public CommandResponse execute(CommandRequest request) {

        String userName = request.getParameter("user");
        User user = userService.findByLogin(userName);
        UserStatus newStatus = UserStatus.valueOf(request.getParameter("status"));
        userService.update(new User(user.getId(), user.getName(), user.getAge(),
                user.getRole().getId(), user.getEmail(), user.getPasswordHash(), newStatus.getId()));

        final HttpSession session = request.getCurrentSession().get();
        String curUserName = (String) session.getAttribute("user");
        User curUser = userService.findByLogin(curUserName);
        List<Movie> films = filmService.findAll();
        List<User> users = userService.findAll();
        List<UserStatus> statuses = userService.findAllStatuses();
        List<FilmGenre> genres = filmService.findAllGenres();

        request.setAttribute(GENRES_ATTRIBUTE, genres);
        request.setAttribute(STATUSES_ATTRIBUTE, statuses);
        request.setAttribute(FILMS_ATTRIBUTE, films);
        request.setAttribute(USERS_ATTRIBUTE, users);
        request.setAttribute(ADMIN_ATTRIBUTE, curUser);
        return new CommandResponse() {
            @Override
            public String getPath() {
                return "/WEB-INF/jsp/admin.jsp";
            }

            @Override
            public boolean isRedirect() {
                return false;
            }
        };
    }
}
