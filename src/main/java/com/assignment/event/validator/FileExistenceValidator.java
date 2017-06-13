package com.assignment.event.validator;

import java.io.File;
import com.assignment.event.exception.InvalidEventDataException;

public class FileExistenceValidator implements Validator {

    /**
     * Validate the input path and if file at input path doesn't exist then throw an exception.
     */
    @Override
    public void validate(final String path) {
        final File file = new File(path);
        if (!file.exists()) {
            throw new InvalidEventDataException("Expected file to be present. Please check that file exists and retry.");
        }
    }
}
