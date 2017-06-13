package com.assignment.event.handler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.assignment.event.ReadEvent;

/**
 * An event event passed to aggregator.
 */
public class StartEventHandler implements EventHandler<ReadEvent> {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    /**
     * An event to log saying, application is about to start reading the file content.
     */
    @Override
    public void process(final ReadEvent readEvent) {
        logger.info("Reading lines of file {}", readEvent.getPath());
    }
}
