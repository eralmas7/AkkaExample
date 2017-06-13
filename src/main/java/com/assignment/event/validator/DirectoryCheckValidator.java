package com.assignment.event.validator;

import java.io.File;
import com.assignment.event.exception.InvalidEventDataException;

public class DirectoryCheckValidator implements Validator {

    /**
     * Validate the input path and if path is a file then throw an exception.
     */
    @Override
    public void validate(final String path) {
        final File file = new File(path);
        if (file.isFile()) {
            throw new InvalidEventDataException("Expected directory but got file. Please check and retry after passing directory.");
        }
    }
}
