package com.assignment.event.exception;

/**
 * Exception thrown when there are problems while writing output to a file.
 */
public class DataWriteException extends RuntimeException {

    private static final long serialVersionUID = -2983977050028214710L;

    public DataWriteException(final String message) {
        super(message);
    }

    public DataWriteException(final String message, final Throwable throwable) {
        super(message, throwable);
    }
}
