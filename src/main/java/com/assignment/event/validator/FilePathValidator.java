package com.assignment.event.validator;

import com.assignment.event.exception.InvalidEventDataException;

public class FilePathValidator implements Validator {

    /**
     * Validate the input path and if input path is null then throw an exception.
     */
    @Override
    public void validate(final String path) {
        if (path == null) {
            throw new InvalidEventDataException("Did not expected null for file path. Please check input and pass the non null path.");
        }
    }
}
