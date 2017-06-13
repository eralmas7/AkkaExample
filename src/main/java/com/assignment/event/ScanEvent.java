package com.assignment.event;

/**
 * An event for the file scanner actor.
 */
public interface ScanEvent {

    /**
     * Returns the path of the file resource.
     * 
     * @return
     */
    public String getPath();
}
