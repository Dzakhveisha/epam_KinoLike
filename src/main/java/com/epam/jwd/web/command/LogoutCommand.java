package com.epam.jwd.web.command;

public class LogoutCommand implements Command{
    @Override
    public CommandResponse execute(CommandRequest request) {
        request.invalidateCurrentSession();
        return new CommandResponse() {
            @Override
            public String getPath() {
                return "/KinoLike/index.jsp";
            }

            @Override
            public boolean isRedirect() {
                return true;
            }
        };
    }
}
