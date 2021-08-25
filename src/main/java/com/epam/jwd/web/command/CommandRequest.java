package com.epam.jwd.web.command;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.servlet.http.Part;
import java.util.Optional;

/**
 * Wrapper of HttpServletRequest
 */
public interface CommandRequest {

    /**
     * create and return HttpSession
     *
     * @return HttpSession
     */
    HttpSession createSession();

    /**
     * if HttpSession exist - return it, else return null
     *
     * @return current HttpSession or null
     */
    Optional<HttpSession> getCurrentSession();

    /**
     * removes session if it exist
     */
    void invalidateCurrentSession();

    /**
     * set attribute
     *
     * @param name of attribute
     * @param value of attribute
     */
    void setAttribute(String name, Object value);

    /**
     * return string value by parameter name
     *
     * @param name parameter name
     * @return value of parameter
     */
    String getParameter(String name);

    /**
     * return part
     *
     * @param part name of Part
     * @return part
     */
    Part getPart(String part);

    /**
     * @return return HttpServletRequest
     */
    HttpServletRequest getReq();
}
