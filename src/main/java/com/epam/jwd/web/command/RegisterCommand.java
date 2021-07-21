package com.epam.jwd.web.command;

import at.favre.lib.crypto.bcrypt.BCrypt;
import com.epam.jwd.web.model.User;
import com.epam.jwd.web.service.UserService;


public class RegisterCommand implements Command {

    private final UserService userService;

    public RegisterCommand() {
        userService = UserService.getInstance();
    }

    @Override
    public CommandResponse execute(CommandRequest request) {
        String password = request.getParameter("password");
        String mail = request.getParameter("mail");
        Integer age = Integer.valueOf(request.getParameter("age"));
        String login = request.getParameter("login");

        User newUser = new User(login, password, mail, age);
        if (userService.canRegister(newUser)){
            userService.create(newUser);
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
            request.setAttribute("error", "This login is already exist!!");
            return new CommandResponse() {
                @Override
                public String getPath() {
                    return "/WEB-INF/jsp/register.jsp";
                }

                @Override
                public boolean isRedirect() {
                    return false;
                }
            };
        }
    }
}

