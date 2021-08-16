package com.epam.jwd.web.command;

import com.epam.jwd.web.model.User;
import com.epam.jwd.web.service.UserService;

import javax.servlet.http.HttpSession;
import java.nio.charset.StandardCharsets;

public class LoginCommand implements Command {

    private static final String PASSWORD_PARAM = "password";
    private static final String LOGIN_PARAM = "login";
    private static final String USER_SESSION_ATTRIBUTE = "user";
    private static final String USER_ROLE_SESSION_ATTRIBUTE = "role";
    private static final String CAN_NOT_LOGIN_MSG = "Wrong login or password!";
    private static final String ERROR_ATTRIBUTE = "error";

    private final UserService userService;

    public LoginCommand() {
        userService = UserService.getInstance();
    }

    @Override
    public CommandResponse execute(CommandRequest request) {
        String password = request.getParameter(PASSWORD_PARAM);
        String login = request.getParameter(LOGIN_PARAM);
        if(password == null || login == null){
            request.setAttribute(ERROR_ATTRIBUTE, "Not enough data!");
            return new SimpleCommandResponse("/WEB-INF/jsp/error.jsp",false);
        }
        password = replaceScripts(new String(password.getBytes(StandardCharsets.ISO_8859_1), StandardCharsets.UTF_8));
        login = replaceScripts(new String(login.getBytes(StandardCharsets.ISO_8859_1), StandardCharsets.UTF_8));
        User logUser = new User(login, password);
        if (userService.canLogIn(logUser)) {
            request.getCurrentSession().ifPresent(HttpSession::invalidate);
            final HttpSession session = request.createSession();
            session.setAttribute(USER_SESSION_ATTRIBUTE, login);
            session.setAttribute(USER_ROLE_SESSION_ATTRIBUTE, userService.findByLogin(login).getRole());
            return new SimpleCommandResponse("/KinoLike/index.jsp", true);

        } else {
            request.setAttribute(ERROR_ATTRIBUTE, CAN_NOT_LOGIN_MSG);
            return new SimpleCommandResponse("/WEB-INF/jsp/error.jsp",false);

        }
    }

    private String replaceScripts(String string) {
        string = string.replaceAll("<", "&lt");
        string = string.replaceAll(">", "&gt");
        return string;
    }
}
