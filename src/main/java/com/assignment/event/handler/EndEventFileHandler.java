package com.assignment.event.handler;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;
import com.assignment.actors.lifecycle.ActorSystemLifecycle;
import com.assignment.event.ReadEvent;
import com.assignment.event.exception.DataWriteException;
import com.google.common.io.Files;
import akka.actor.ActorRef;
import akka.actor.PoisonPill;

/**
 * An event event passed to aggregator.
 */
public class EndEventFileHandler implements EventHandler<ReadEvent> {

    private final Map<String, Long> fileLineCountMap;
    private final File outputFilePath;
    private final AtomicInteger counter;
    private final ActorSystemLifecycle actorSystemLifecycle;

    public EndEventFileHandler(final Map<String, Long> fileLineCountMap, final String outputFilePath, final AtomicInteger counter, final ActorSystemLifecycle actorSystemLifecycle) {
        this.fileLineCountMap = fileLineCountMap;
        this.outputFilePath = new File(outputFilePath);
        this.counter = counter;
        this.actorSystemLifecycle = actorSystemLifecycle;
    }

    private void createFileIfNotExist() throws IOException {
        if (!this.outputFilePath.exists()) {
            this.outputFilePath.createNewFile();
        }
    }

    private void cleanUp(final ReadEvent readEvent) {
        if (counter.decrementAndGet() == 0) {
            readEvent.getActorRef().tell(PoisonPill.getInstance(), ActorRef.noSender());
            actorSystemLifecycle.stopPipeline();
        }
    }

    private String getFileNameFromPath(final String path) {
        final String[] paths = path.split("\\\\");
        return paths[paths.length - 1];
    }

    /**
     * Write the file's word count represented by event to a file as a string.
     */
    @Override
    public void process(final ReadEvent readEvent) {
        final long wordCount = fileLineCountMap.remove(readEvent.getPath());
        final String string = String.format("%s,%d%n", getFileNameFromPath(readEvent.getPath()), wordCount);
        try {
            createFileIfNotExist();
            Files.append(string, outputFilePath, StandardCharsets.UTF_8);
        } catch (IOException ioException) {
            throw new DataWriteException("Got unwanted exception while writing the output to file " + outputFilePath.getAbsolutePath(), ioException);
        } finally {
            cleanUp(readEvent);
        }
    }
}
