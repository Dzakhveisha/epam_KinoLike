package com.epam.jwd.web.command;

/**
 * command response containing page path and routing type
 */
public interface CommandResponse {

    /**
     * @return Path of the page
     */
    String getPath();

    /**
     * @return routing type
     */
    boolean isRedirect();
}
