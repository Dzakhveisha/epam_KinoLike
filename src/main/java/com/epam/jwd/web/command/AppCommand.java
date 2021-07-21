package com.epam.jwd.web.command;

import com.epam.jwd.web.command.page.*;
import com.epam.jwd.web.model.UserRole;

import java.util.Arrays;
import java.util.List;

import static com.epam.jwd.web.model.UserRole.*;

public enum AppCommand {
    MAIN_PAGE(new ShowMainPageCommand()),
    SHOW_LOGIN(new ShowLoginPageCommand(), UNAUTHORIZED),
    SHOW_ADMIN(new ShowAdminPageCommand(), ADMIN),
    SHOW_REGISTER(new ShowRegisterPageCommand(), UNAUTHORIZED),
    SHOW_LIKES(new ShowLikesPageCommand()),
    REGISTER(new RegisterCommand(), UNAUTHORIZED),
    LOGIN(new LoginCommand(), UNAUTHORIZED),
    LOGOUT(new LogoutCommand(), ADMIN, USER),
    DEFAULT(new ShowMainPageCommand()),
    ERROR(new ShowErrorPageCommand());

    private final Command command;
    private final List<UserRole> availableRoles;

    AppCommand(Command command, UserRole... roles) {
        this.command = command;
        availableRoles = roles.length != 0 ? Arrays.asList(roles) : UserRole.valuesAsList();
    }

    public Command getCommand() {
        return command;
    }

    public List<UserRole> getAvailableRoles() {
        return availableRoles;
    }

    public static AppCommand of(String name) {
        for (AppCommand command : values()) {
            if (command.name().equalsIgnoreCase(name)) {
                return command;
            }
        }
        return DEFAULT;
    }
}
