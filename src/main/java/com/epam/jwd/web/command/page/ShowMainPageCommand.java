package com.epam.jwd.web.command.page;

import com.epam.jwd.web.command.Command;
import com.epam.jwd.web.command.CommandRequest;
import com.epam.jwd.web.command.CommandResponse;
import com.epam.jwd.web.model.FilmGenre;
import com.epam.jwd.web.model.Movie;
import com.epam.jwd.web.service.FilmService;

import java.util.List;

public class ShowMainPageCommand implements Command {

    private static final String FILMS_ATTRIBUTE = "films";
    private static final String GENRES_ATTRIBUTE = "genres";
    public static final String SORT_PARAMETER = "sort";
    public static final String GENRE_PARAMETER = "genre";
    public static final String SORT_TYPE_NEW = "new";
    public static final String SORT_TYPE_POPULAR = "popular";

    private final FilmService FILM_SERVICE;

    public ShowMainPageCommand() {
        FILM_SERVICE = FilmService.getInstance();
    }

    @Override
    public CommandResponse execute(CommandRequest request) {
        List<FilmGenre> genres = FILM_SERVICE.findAllGenres();
        List<Movie> films = null;
        String sortType = request.getParameter(SORT_PARAMETER);
        if (sortType != null) {
            switch (sortType) {
                case SORT_TYPE_NEW:
                    films = FILM_SERVICE.findAllNew();
                    break;
                case SORT_TYPE_POPULAR:
                    films = FILM_SERVICE.findAllPopular();
                    break;
            }
        } else {
            films = FILM_SERVICE.findAll();
        }
        String genreName = request.getParameter(GENRE_PARAMETER);
        if (genreName != null) {
            films = FILM_SERVICE.finAllByGenre(FilmGenre.valueOf(genreName));
        }
        request.setAttribute(FILMS_ATTRIBUTE, films);
        request.setAttribute(GENRES_ATTRIBUTE, genres);
        return new CommandResponse() {
            @Override
            public String getPath() {
                return "/WEB-INF/jsp/main.jsp";
            }

            @Override
            public boolean isRedirect() {
                return false;
            }
        };
    }
}
