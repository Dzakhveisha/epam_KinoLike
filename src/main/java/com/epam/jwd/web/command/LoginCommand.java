package com.epam.jwd.web.command;

import com.epam.jwd.web.model.User;
import com.epam.jwd.web.service.UserService;

import javax.servlet.http.HttpSession;

public class LoginCommand implements Command {

    private static final String PASSWORD_PARAM = "password";
    private static final String LOGIN_PARAM = "login";
    private static final String USER_SESSION_ATTRIBUTE = "user";
    private static final String USER_ROLE_SESSION_ATTRIBUTE = "role";

    private final UserService userService;

    public LoginCommand() {
        userService = UserService.getInstance();
    }

    @Override
    public CommandResponse execute(CommandRequest request) {
        String password = request.getParameter(PASSWORD_PARAM);
        String login = request.getParameter(LOGIN_PARAM);
        User logUser = new User(login, password);
        if (userService.canLogIn(logUser)) {
            request.getCurrentSession().ifPresent(HttpSession::invalidate);
            final HttpSession session = request.createSession();
            session.setAttribute(USER_SESSION_ATTRIBUTE, login);
            session.setAttribute(USER_ROLE_SESSION_ATTRIBUTE, userService.findByLogin(login).getRole());
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
        } else {
            request.setAttribute("error", "Wrong login or password!");
            return new CommandResponse() {
                @Override
                public String getPath() {
                    return "/WEB-INF/jsp/login.jsp";
                }

                @Override
                public boolean isRedirect() {
                    return false;
                }
            };
        }
    }
}
