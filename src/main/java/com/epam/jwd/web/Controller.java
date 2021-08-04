package com.epam.jwd.web;

import com.epam.jwd.web.command.Command;
import com.epam.jwd.web.command.CommandRequest;
import com.epam.jwd.web.command.CommandResponse;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.util.Optional;

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
        // todo написать реализацию!!!
        final CommandResponse response = command.execute(new CommandRequest() {
            @Override
            public HttpSession createSession() {
                return req.getSession(true);
            }

            @Override
            public Optional<HttpSession> getCurrentSession() {
                return Optional.ofNullable(req.getSession(false));
            }

            @Override
            public void invalidateCurrentSession() {
                final HttpSession session = req.getSession(false);
                if (session != null){
                    session.invalidate();
                }
            }

            @Override
            public void setAttribute(String name, Object value) {
                req.setAttribute(name, value);
            }

            @Override
            public String getParameter(String name) {
                return req.getParameter(name);
            }

            @Override
            public Part getPart(String part) {
                try {
                    return req.getPart(part);
                } catch (IOException | ServletException e) {
                    e.printStackTrace();
                    return null;
                }
            }

            @Override
            public HttpServletRequest getReq() {
                return req;
            }
        });
        if (response.isRedirect()){
            resp.sendRedirect(response.getPath());
        } else{
            final RequestDispatcher dispatcher = req.getRequestDispatcher(response.getPath());
            dispatcher.forward(req, resp);
        }
    }
}
