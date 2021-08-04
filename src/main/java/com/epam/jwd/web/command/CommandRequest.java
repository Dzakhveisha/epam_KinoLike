package com.epam.jwd.web.command;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.servlet.http.Part;
import java.util.Optional;

public interface CommandRequest {

    HttpSession createSession();

    Optional<HttpSession> getCurrentSession();

    void invalidateCurrentSession();

    void setAttribute(String name, Object value);

    String getParameter(String name);

    Part getPart(String part);

    HttpServletRequest getReq();
}
