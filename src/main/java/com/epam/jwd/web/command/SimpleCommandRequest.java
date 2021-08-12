package com.epam.jwd.web.command;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.servlet.http.Part;
import java.io.IOException;
import java.util.Optional;

public class SimpleCommandRequest implements CommandRequest{

    private HttpServletRequest req;

    public SimpleCommandRequest(HttpServletRequest req) {
        this.req = req;
    }

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
}
