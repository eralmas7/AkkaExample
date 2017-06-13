package com.assignment.event.handler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.assignment.event.ScanEvent;

/**
 * An unknown event passed to file scanner.
 */
public class DefaultScanEventHandler implements EventHandler<ScanEvent> {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    /**
     * Handling of an unknown event passed to the file scanner.
     */
    @Override
    public void process(final ScanEvent scanEvent) {
        logger.warn("Unknown event recieved of type {} which will be ignored", scanEvent);
    }
}
