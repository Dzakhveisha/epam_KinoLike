package com.epam.jwd.web.command;

import com.epam.jwd.web.model.FilmGenre;
import com.epam.jwd.web.model.Movie;
import com.epam.jwd.web.service.FilmService;

import java.nio.charset.StandardCharsets;

public class NewFilmCommand implements Command {

    private static final String PARAMETER_NAME = "name";
    private static final String PARAMETER_YEAR = "year";
    private static final String PARAMETER_DESCRIPTION = "descript";
    private static final String PARAMETER_COUNTRY = "country";
    private static final String PARAMETER_GENRE = "genre";
    private final FilmService filmService;

    public NewFilmCommand() {
        filmService = FilmService.getInstance();
    }

    @Override
    public CommandResponse execute(CommandRequest request) {
        String name = new String(request.getParameter(PARAMETER_NAME).getBytes(StandardCharsets.ISO_8859_1), StandardCharsets.UTF_8);
        String description = new String(request.getParameter(PARAMETER_DESCRIPTION).getBytes(StandardCharsets.ISO_8859_1), StandardCharsets.UTF_8);
        Integer year = Integer.parseInt(request.getParameter(PARAMETER_YEAR));
        String country = new String(request.getParameter(PARAMETER_COUNTRY).getBytes(StandardCharsets.ISO_8859_1), StandardCharsets.UTF_8);
        FilmGenre genre = FilmGenre.valueOf(request.getParameter(PARAMETER_GENRE));



        filmService.create(new Movie(name, year, description, 1, genre.getId()));
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
