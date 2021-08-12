package com.epam.jwd.web.command;

public class LogoutCommand implements Command{
    @Override
    public CommandResponse execute(CommandRequest request) {
        request.invalidateCurrentSession();
        return new SimpleCommandResponse("/KinoLike/index.jsp",true);
    }
}
