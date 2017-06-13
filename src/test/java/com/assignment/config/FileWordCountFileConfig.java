package com.assignment.config;

import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;
import com.assignment.actors.lifecycle.ActorSystemLifecycle;
import com.assignment.event.ReadEvent;
import com.assignment.event.handler.EndEventFileHandler;
import com.assignment.event.handler.EventHandler;

/**
 * Configuration for the file word count application.
 */
public class FileWordCountFileConfig extends FileWordCountConfig {

    private static final String outputFileLocation = System.getProperty("assignment.result.file.path", "/temp/almas.txt");

    @Override
    public EventHandler<ReadEvent> getEndEventHandler(final Map<String, Long> fileLineCountMap, final AtomicInteger counter, final ActorSystemLifecycle actorSystemLifecycle) {
        return new EndEventFileHandler(fileLineCountMap, outputFileLocation, counter, actorSystemLifecycle);
    }
}
