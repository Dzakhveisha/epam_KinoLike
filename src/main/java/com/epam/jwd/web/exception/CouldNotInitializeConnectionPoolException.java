package com.epam.jwd.web.exception;

public class CouldNotInitializeConnectionPoolException extends ConnectionPoolException{

    public CouldNotInitializeConnectionPoolException(String message, Throwable cause){
        super(message, cause);
    }

    public CouldNotInitializeConnectionPoolException(String message){
        super(message);
    }

}
