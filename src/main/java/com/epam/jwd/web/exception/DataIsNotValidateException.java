package com.epam.jwd.web.exception;


/**
 * Throws when data fails validation
 */
public class DataIsNotValidateException extends Exception {
    public DataIsNotValidateException() {
    }

    public DataIsNotValidateException(String message) {
        super(message);
    }
}
