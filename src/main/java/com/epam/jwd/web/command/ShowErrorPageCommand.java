package com.epam.jwd.web.command;

public class ShowErrorPageCommand implements Command{

    @Override
    public CommandResponse execute(CommandRequest request) {
        return new CommandResponse() {
            @Override
            public String getPath() {
                return "/WEB-INF/jsp/error.jsp";
            }

            @Override
            public boolean isRedirect() {
                return false;
            }
        };
    }
}
