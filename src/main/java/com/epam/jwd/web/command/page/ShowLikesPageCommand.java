package com.epam.jwd.web.command.page;

import com.epam.jwd.web.command.Command;
import com.epam.jwd.web.command.CommandRequest;
import com.epam.jwd.web.command.CommandResponse;
import com.epam.jwd.web.model.Movie;
import com.epam.jwd.web.service.FilmService;

import java.util.List;

public class ShowLikesPageCommand implements Command {

    private static final String FILMS_ATTRIBUTE = "films";

    private final FilmService FILM_SERVICE;

    public ShowLikesPageCommand() {
        FILM_SERVICE = FilmService.getInstance();
    }

    @Override
    public CommandResponse execute(CommandRequest request) {
        List<Movie> films = FILM_SERVICE.findLikedFilms();
        request.setAttribute(FILMS_ATTRIBUTE, films);
        return new CommandResponse() {
            @Override
            public String getPath() {
                return "/WEB-INF/jsp/likes.jsp";
            }

            @Override
            public boolean isRedirect() {
                return false;
            }
        };
    }
}
