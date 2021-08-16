package com.epam.jwd.web.command;

import com.epam.jwd.web.Validator.Validator;
import com.epam.jwd.web.exception.DataIsNotValidateException;
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
    private final Validator dataValidator;

    public RegisterCommand() {
        userService = UserService.getInstance();
        dataValidator = Validator.getInstance();
    }

    @Override
    public CommandResponse execute(CommandRequest request) {
        String password = request.getParameter(PASSWORD_PARAMETER);
        String mail = request.getParameter(MAIL_PARAMETER);
        String login = request.getParameter(LOGIN_PARAMETER);
        String ageString = request.getParameter(AGE_PARAMETER);
        int age = 0;
        if (password == null || mail == null || login == null) {
            request.setAttribute(ERROR_ATTRIBUTE, "Not all data is entered!");
            return new SimpleCommandResponse("/WEB-INF/jsp/error.jsp", false);
        }
        password = replaceScripts(new String(password.getBytes(StandardCharsets.ISO_8859_1), StandardCharsets.UTF_8));
        mail = replaceScripts(new String(mail.getBytes(StandardCharsets.ISO_8859_1), StandardCharsets.UTF_8));
        login = replaceScripts(new String(login.getBytes(StandardCharsets.ISO_8859_1), StandardCharsets.UTF_8));
        age = Integer.parseInt(ageString);

        User newUser = new User(login, password, mail, age);
        try {
            dataValidator.validateUser(newUser);
        } catch (DataIsNotValidateException e) {
            //todo log
            request.setAttribute(ERROR_ATTRIBUTE, e.getMessage());
            return new SimpleCommandResponse("/WEB-INF/jsp/error.jsp", false);
        }

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

