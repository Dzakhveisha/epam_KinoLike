package com.epam.jwd.web.command.page;

import com.epam.jwd.web.command.Command;
import com.epam.jwd.web.command.CommandRequest;
import com.epam.jwd.web.command.CommandResponse;

import java.nio.charset.StandardCharsets;


public class ShowReviewPageCommand implements Command {

    @Override
    public CommandResponse execute(CommandRequest request) {
        final String film = new String(request.getParameter("film").getBytes(StandardCharsets.ISO_8859_1), StandardCharsets.UTF_8);
        request.setAttribute("film", film);
        return new CommandResponse() {
            @Override
            public String getPath() {
                return "/WEB-INF/jsp/review.jsp";
            }

            @Override
            public boolean isRedirect() {
                return false;
            }
        };
    }
}
