package com.assignment.event.validator;

import java.io.File;
import com.assignment.event.exception.InvalidEventDataException;

public class FilePermissionValidator implements Validator {

    /**
     * Validate the input path and if input path is not readable then throw an exception.
     */
    @Override
    public void validate(final String path) {
        final File file = new File(path);
        if (!file.canRead()) {
            throw new InvalidEventDataException("Unable to read the file. Please check and retry after correcting the permission to file.");
        }
    }
}
