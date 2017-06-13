package com.assignment.event.validator;

import java.io.File;
import com.assignment.event.exception.InvalidEventDataException;

public class FileCheckValidator implements Validator {

    /**
     * Validate the input path and if input path is a directory then throw an exception.
     */
    @Override
    public void validate(final String path) {
        final File file = new File(path);
        if (!file.isFile()) {
            throw new InvalidEventDataException("Expected file but got Directory. Please check and retry after passing file.");
        }
    }
}
