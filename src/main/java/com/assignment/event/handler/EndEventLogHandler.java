package com.assignment.event.handler;

import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.assignment.actors.lifecycle.ActorSystemLifecycle;
import com.assignment.event.ReadEvent;
import akka.actor.ActorRef;
import akka.actor.PoisonPill;

/**
 * An event event passed to aggregator.
 */
public class EndEventLogHandler implements EventHandler<ReadEvent> {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    private final Map<String, Long> lineCount;
    private final ActorSystemLifecycle actorSystemLifecycle;
    private final AtomicInteger counter;

    public EndEventLogHandler(final Map<String, Long> lineCount, final AtomicInteger counter, final ActorSystemLifecycle actorSystemLifecycle) {
        this.lineCount = lineCount;
        this.actorSystemLifecycle = actorSystemLifecycle;
        this.counter = counter;
    }

    private void cleanUp(final ReadEvent readEvent) {
        if (counter.decrementAndGet() == 0) {
            readEvent.getActorRef().tell(PoisonPill.getInstance(), ActorRef.noSender());
            actorSystemLifecycle.stopPipeline();
        }
    }

    /**
     * Log the file's word count represented by event over the console as a string.
     */
    @Override
    public void process(final ReadEvent readEvent) {
        try {
            logger.info("Line count of file : {} is : {}", readEvent.getPath(), lineCount.remove(readEvent.getPath()));
        } finally {
            cleanUp(readEvent);
        }
    }
}
