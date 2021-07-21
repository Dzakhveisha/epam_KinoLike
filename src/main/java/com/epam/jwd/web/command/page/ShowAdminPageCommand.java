package com.epam.jwd.web.command.page;

import com.epam.jwd.web.command.Command;
import com.epam.jwd.web.command.CommandRequest;
import com.epam.jwd.web.command.CommandResponse;

public class ShowAdminPageCommand implements Command {
    @Override
    public CommandResponse execute(CommandRequest request) {
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