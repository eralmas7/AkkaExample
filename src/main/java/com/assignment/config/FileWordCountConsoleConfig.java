package com.assignment.config;

import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;
import com.assignment.actors.lifecycle.ActorSystemLifecycle;
import com.assignment.event.ReadEvent;
import com.assignment.event.handler.EndEventLogHandler;
import com.assignment.event.handler.EventHandler;

/**
 * Configuration for the file word count application.
 */
public class FileWordCountConsoleConfig extends FileWordCountConfig {

    @Override
    public EventHandler<ReadEvent> getEndEventHandler(final Map<String, Long> fileLineCountMap, final AtomicInteger counter, final ActorSystemLifecycle actorSystemLifecycle) {
        return new EndEventLogHandler(fileLineCountMap, counter, actorSystemLifecycle);
    }
}
