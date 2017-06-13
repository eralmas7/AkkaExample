package com.assignment.event.handler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.assignment.event.ReadEvent;

/**
 * An unknown event passed to aggregator.
 */
public class DefaultReadEventHandler implements EventHandler<ReadEvent> {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    /**
     * Handling of an unknown event passed to the aggregator.
     */
    @Override
    public void process(final ReadEvent readEvent) {
        logger.warn("Unknown event recieved of type {} which will be ignored", readEvent);
    }
}
