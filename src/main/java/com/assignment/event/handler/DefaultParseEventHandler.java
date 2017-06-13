package com.assignment.event.handler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.assignment.event.ParseEvent;

/**
 * An unknown event passed to parser.
 */
public class DefaultParseEventHandler implements EventHandler<ParseEvent> {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    /**
     * Handling of an unknown event passed to the parser.
     */
    @Override
    public void process(final ParseEvent parseEvent) {
        logger.warn("Unknown event recieved of type {} which will be ignored", parseEvent);
    }
}
