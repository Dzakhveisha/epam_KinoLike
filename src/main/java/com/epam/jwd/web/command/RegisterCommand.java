package com.epam.jwd.web.command;

import com.epam.jwd.web.model.User;
import com.epam.jwd.web.service.UserService;

import java.nio.charset.StandardCharsets;


public class RegisterCommand implements Command {

    private static final String PASSWORD_PARAMETER = "password";
    private static final String MAIL_PARAMETER = "mail";
    private static final String AGE_PARAMETER = "age";
    private static final String LOGIN_PARAMETER = "login";
    private static final Object LOGIN_IS_EXIST_MSG = "This login is already exist!!";
    private static final String ERROR_ATTRIBUTE = "error";
    private final UserService userService;

    public RegisterCommand() {
        userService = UserService.getInstance();
    }

    @Override
    public CommandResponse execute(CommandRequest request) {
        String password = replaceScripts(new String(request.getParameter(PASSWORD_PARAMETER).getBytes(StandardCharsets.ISO_8859_1), StandardCharsets.UTF_8));
        String mail = replaceScripts(new String(request.getParameter(MAIL_PARAMETER).getBytes(StandardCharsets.ISO_8859_1), StandardCharsets.UTF_8));
        Integer age = Integer.valueOf(request.getParameter(AGE_PARAMETER));
        String login = replaceScripts(new String(request.getParameter(LOGIN_PARAMETER).getBytes(StandardCharsets.ISO_8859_1), StandardCharsets.UTF_8));

        User newUser = new User(login, password, mail, age);
        if (userService.canRegister(newUser)) {
            userService.create(newUser);
            return new SimpleCommandResponse("/KinoLike/index.jsp", true);
        } else {
            request.setAttribute(ERROR_ATTRIBUTE, LOGIN_IS_EXIST_MSG);
            return new SimpleCommandResponse("/WEB-INF/jsp/error.jsp", false);
        }
    }

    private String replaceScripts(String string) {
        string = string.replaceAll("<", "&lt");
        string = string.replaceAll(">", "&gt");
        return string;
    }
}

