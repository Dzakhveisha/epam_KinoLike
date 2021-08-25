package com.epam.jwd.web.exception;

/**
 * Throws when it is impossible to initialize connection pool
 */
public class CouldNotInitializeConnectionPoolException extends Exception{

    public CouldNotInitializeConnectionPoolException(String message, Throwable cause){
        super(message, cause);
    }

    public CouldNotInitializeConnectionPoolException(String message){
        super(message);
    }

}
