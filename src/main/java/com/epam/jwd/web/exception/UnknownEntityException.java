package com.epam.jwd.web.exception;

public class UnknownEntityException extends RuntimeException {
    public UnknownEntityException() {
    }

    public UnknownEntityException(String message) {
        super(message);
    }
}
