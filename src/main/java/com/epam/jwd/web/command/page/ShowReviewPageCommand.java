package com.epam.jwd.web.command.page;

import com.epam.jwd.web.command.Command;
import com.epam.jwd.web.command.CommandRequest;
import com.epam.jwd.web.command.CommandResponse;
import com.epam.jwd.web.command.SimpleCommandResponse;

import java.nio.charset.StandardCharsets;


public class ShowReviewPageCommand implements Command {

    private static final String FILM_PARAMETER = "film";
    private static final String FILM_ATTRIBUTE = "film";

    @Override
    public CommandResponse execute(CommandRequest request) {
        final String film = new String(request.getParameter(FILM_PARAMETER).getBytes(StandardCharsets.ISO_8859_1), StandardCharsets.UTF_8);
        request.setAttribute(FILM_ATTRIBUTE, film);
        return new SimpleCommandResponse("/WEB-INF/jsp/review.jsp", false);
    }
}
