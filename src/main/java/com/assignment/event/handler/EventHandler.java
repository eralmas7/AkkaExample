package com.assignment.event.handler;

/**
 * An event handler to process the input event.
 * 
 * @param <I>
 */
public interface EventHandler<I> {

    /**
     * Process the input event.
     * 
     * @param inputEvent
     */
    public void process(final I inputEvent);
}
