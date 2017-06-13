package com.assignment.event;

/**
 * Scan event from main application to start picking up the files from given directory resource.
 */
public class DirectoryScanEvent implements ScanEvent {

    private final String path;

    public DirectoryScanEvent(final String path) {
        this.path = path;
    }

    @Override
    public String getPath() {
        return path;
    }
}
