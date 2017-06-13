package com.assignment.actors;

import java.util.Map;
import com.assignment.event.DefaultReadEvent;
import com.assignment.event.ReadEvent;
import com.assignment.event.handler.EventHandler;
import akka.actor.AbstractActor;

/**
 * The Aggregator actor split words in the lines by the space “ “ character based on the “line”
 * event The Aggregator actor counts the number of words in a file The Aggregator actor prints the
 * number of words in a file in the console when it receives the “end-of-file” event
 */
public class AggregatorActor extends AbstractActor {

    private final Map<Class<? extends ReadEvent>, EventHandler<ReadEvent>> eventTypeHandlerMap;

    public AggregatorActor(final Map<Class<? extends ReadEvent>, EventHandler<ReadEvent>> eventTypeHandlerMap) {
        this.eventTypeHandlerMap = eventTypeHandlerMap;
    }

    /**
     * A Listener which would receive event from file parser to count the words in a file.
     */
    // To save from open close principle and make it easy to unit test
    @Override
    public Receive createReceive() {
        return receiveBuilder().matchAny(object -> eventTypeHandlerMap.getOrDefault(object.getClass(), eventTypeHandlerMap.get(DefaultReadEvent.class)).process((ReadEvent) object)).build();
    }
}
