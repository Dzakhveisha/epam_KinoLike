package com.epam.jwd.web.exception;

/**
 * Throws when when the specified entity cannot be found in the database
 */
public class UnknownEntityException extends RuntimeException {
    public UnknownEntityException() {
    }

    public UnknownEntityException(String message) {
        super(message);
    }
}
