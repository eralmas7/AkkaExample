package com.assignment.event.handler;

import java.util.Map;
import com.assignment.event.FileLineReadEvent;
import com.assignment.event.ReadEvent;

/**
 * An event event passed to aggregator.
 */
public class LineEventHandler implements EventHandler<ReadEvent> {

    private final Map<String, Long> lineCount;

    public LineEventHandler(final Map<String, Long> lineCount) {
        this.lineCount = lineCount;
    }

    /**
     * Count the number of words in a file and store it in a map.
     */
    @Override
    public void process(final ReadEvent readEvent) {
        final FileLineReadEvent fileLineReadEvent = (FileLineReadEvent) readEvent;
        final long value = getWordCount(fileLineReadEvent.getLine());
        lineCount.compute(fileLineReadEvent.getPath(), (k, v) -> v == null ? value : v + value);
    }

    private long getWordCount(final String line) {
        return line.split("\\s+").length;
    }
}
