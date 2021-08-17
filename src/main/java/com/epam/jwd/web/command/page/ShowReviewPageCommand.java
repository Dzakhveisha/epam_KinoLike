package com.epam.jwd.web.command.page;

import com.epam.jwd.web.command.Command;
import com.epam.jwd.web.command.CommandRequest;
import com.epam.jwd.web.command.CommandResponse;
import com.epam.jwd.web.command.SimpleCommandResponse;

import java.nio.charset.StandardCharsets;


public class ShowReviewPageCommand implements Command {

    private static final String FILM_PARAMETER = "film";
    private static final String FILM_ATTRIBUTE = "film";
    private static final String ERROR_ATTRIBUTE = "error";
    private static final String NOT_ENOUGH_DATA_MSG = "Not enough data!";

    @Override
    public CommandResponse execute(CommandRequest request) {
        String film = request.getParameter(FILM_PARAMETER);
        if(film == null){
            request.setAttribute(ERROR_ATTRIBUTE, NOT_ENOUGH_DATA_MSG);
            return new SimpleCommandResponse("/WEB-INF/jsp/error.jsp",false);
        }
        film = new String(film.getBytes(StandardCharsets.ISO_8859_1), StandardCharsets.UTF_8)
                .replaceAll("<","&lt").replaceAll(">","&gt");
        request.setAttribute(FILM_ATTRIBUTE, film);
        return new SimpleCommandResponse("/WEB-INF/jsp/review.jsp", false);
    }
}
