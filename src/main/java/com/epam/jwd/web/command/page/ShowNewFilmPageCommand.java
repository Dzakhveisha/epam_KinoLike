package com.epam.jwd.web.command.page;

import com.epam.jwd.web.command.Command;
import com.epam.jwd.web.command.CommandRequest;
import com.epam.jwd.web.command.CommandResponse;
import com.epam.jwd.web.command.SimpleCommandResponse;
import com.epam.jwd.web.model.FilmGenre;
import com.epam.jwd.web.service.FilmService;

import java.util.List;

public class ShowNewFilmPageCommand implements Command {
    private static final String GENRES_ATTRIBUTE = "genres";
    private final FilmService FILM_SERVICE;

    public ShowNewFilmPageCommand() {
        FILM_SERVICE = FilmService.getInstance();
    }

    @Override
    public CommandResponse execute(CommandRequest request) {
        List<FilmGenre> genres = FILM_SERVICE.findAllGenres();
        request.setAttribute(GENRES_ATTRIBUTE, genres);
        return new SimpleCommandResponse("/WEB-INF/jsp/newFilm.jsp", false);

    }
}
