package com.epam.jwd.web;

import com.epam.jwd.web.command.Command;
import com.epam.jwd.web.command.CommandResponse;
import com.epam.jwd.web.command.SimpleCommandRequest;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;

@WebServlet
@MultipartConfig
public class Controller extends HttpServlet {

    private static final String COMMAND_TYPE_PARAM = "command";


    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        process(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        process(req, resp);

    }

    public static void process(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        String commandType = req.getParameter(COMMAND_TYPE_PARAM);
        req.setCharacterEncoding("UTF-8");

        final Command command = Command.ofName(commandType);
        final CommandResponse response = command.execute(new SimpleCommandRequest(req));
        if (response.isRedirect()){
            resp.sendRedirect(response.getPath());
        } else{
            final RequestDispatcher dispatcher = req.getRequestDispatcher(response.getPath());
            dispatcher.forward(req, resp);
        }
    }
}
