package com.epam.jwd.web.command;

import at.favre.lib.crypto.bcrypt.BCrypt;
import com.epam.jwd.web.model.User;
import com.epam.jwd.web.service.UserService;


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
        String password = request.getParameter(PASSWORD_PARAMETER);
        String mail = request.getParameter(MAIL_PARAMETER);
        Integer age = Integer.valueOf(request.getParameter(AGE_PARAMETER));
        String login = request.getParameter(LOGIN_PARAMETER);

        User newUser = new User(login, password, mail, age);
        if (userService.canRegister(newUser)){
            userService.create(newUser);
            return new SimpleCommandResponse("/KinoLike/index.jsp", true);
        } else {
            request.setAttribute(ERROR_ATTRIBUTE, LOGIN_IS_EXIST_MSG);
            return new SimpleCommandResponse("/WEB-INF/jsp/error.jsp", false);
        }
    }
}

