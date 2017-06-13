package com.assignment.event;

/**
 * An event for the parser actor.
 */
public interface ParseEvent {

    /**
     * Returns the path of the file resource.
     * 
     * @return
     */
    public String getPath();
}
