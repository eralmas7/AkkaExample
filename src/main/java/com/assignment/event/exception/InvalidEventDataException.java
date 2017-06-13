package com.assignment.event.exception;

/**
 * Exception thrown when the event data passed by the actor is invalid.
 */
public class InvalidEventDataException extends RuntimeException {

    private static final long serialVersionUID = -8588849778538739367L;

    public InvalidEventDataException(final String message) {
        super(message);
    }

    public InvalidEventDataException(final String message, final Throwable throwable) {
        super(message, throwable);
    }
}
