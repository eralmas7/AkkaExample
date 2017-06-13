package com.assignment.event;

/**
 * Parse event from file scanner to start parsing the file resource.
 */
public class FileParseEvent implements ParseEvent {

    private final String path;

    public FileParseEvent(final String path) {
        this.path = path;
    }

    @Override
    public String getPath() {
        return path;
    }
}
