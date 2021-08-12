package com.epam.jwd.web.command;

import com.epam.jwd.web.command.Command;
import com.epam.jwd.web.command.CommandRequest;
import com.epam.jwd.web.command.CommandResponse;
import com.epam.jwd.web.exception.UnknownEntityException;
import com.epam.jwd.web.model.FilmGenre;
import com.epam.jwd.web.model.Movie;
import com.epam.jwd.web.model.User;
import com.epam.jwd.web.model.UserStatus;
import com.epam.jwd.web.service.FilmService;
import com.epam.jwd.web.service.UserService;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.nio.charset.StandardCharsets;
import java.util.List;

public class DeleteFilmCommand implements Command {
    private static final String FILMS_ATTRIBUTE = "films";
    private static final String USERS_ATTRIBUTE = "users";
    private static final String ADMIN_ATTRIBUTE = "admin";
    private static final String STATUSES_ATTRIBUTE = "statuses";
    private static final String GENRES_ATTRIBUTE = "genres";
    private static final String USER_ATTRIBUTE = "user";
    private static final String FILM_PARAMETER = "film";
    private final FilmService filmService;
    private final UserService userService;

    public DeleteFilmCommand() {
        filmService = FilmService.getInstance();
        userService = UserService.getInstance();
    }

    @Override
    public CommandResponse execute(CommandRequest request) {
        String name = new String(request.getParameter(FILM_PARAMETER).getBytes(StandardCharsets.ISO_8859_1),StandardCharsets.UTF_8);
        try {
            deleteImage(request.getReq(), filmService.findByName(name).getImagePath());
            filmService.deleteByName(name);
        }
        catch (UnknownEntityException e){
            //todo log
        }

        final HttpSession session = request.getCurrentSession().get();
        String userName = (String) session.getAttribute(USER_ATTRIBUTE);
        User curUser = userService.findByLogin(userName);
        List<Movie> films = filmService.findAll();
        List<User> users = userService.findAll();
        List<UserStatus> statuses = userService.findAllStatuses();
        List<FilmGenre> genres = filmService.findAllGenres();

        request.setAttribute(GENRES_ATTRIBUTE, genres);
        request.setAttribute(STATUSES_ATTRIBUTE, statuses);
        request.setAttribute(FILMS_ATTRIBUTE, films);
        request.setAttribute(USERS_ATTRIBUTE, users);
        request.setAttribute(ADMIN_ATTRIBUTE, curUser);
        return new SimpleCommandResponse("/KinoLike/controller?command=show_admin",true);
    }

    private void deleteImage(HttpServletRequest request, String imagePath) {
        ServletContext servletContext = request.getServletContext();
        String uploadDirectory = servletContext.getInitParameter("IMAGE_UPLOAD_PATH");
        String savePath = uploadDirectory + imagePath;
        File file = new File(savePath);
        file.delete();
    }
}
