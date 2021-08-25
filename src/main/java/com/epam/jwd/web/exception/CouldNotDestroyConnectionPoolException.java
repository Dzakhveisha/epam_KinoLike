package com.epam.jwd.web.exception;


/**
 * Throws when it is impossible to destroy connection pool
 */
public class CouldNotDestroyConnectionPoolException extends Exception{
    public CouldNotDestroyConnectionPoolException(String message) {
        super(message);
    }

    public CouldNotDestroyConnectionPoolException(String message, Throwable cause) {
        super(message, cause);
    }
}
