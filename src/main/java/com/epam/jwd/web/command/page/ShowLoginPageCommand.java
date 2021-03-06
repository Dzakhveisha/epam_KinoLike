package com.epam.jwd.web.command.page;

import com.epam.jwd.web.command.Command;
import com.epam.jwd.web.command.CommandRequest;
import com.epam.jwd.web.command.CommandResponse;
import com.epam.jwd.web.command.SimpleCommandResponse;

public class ShowLoginPageCommand implements Command {
    @Override
    public CommandResponse execute(CommandRequest request) {
        return new SimpleCommandResponse("/WEB-INF/jsp/login.jsp", false);

    }
}
