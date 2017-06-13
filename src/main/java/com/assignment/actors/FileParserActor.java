package com.assignment.actors;

import java.util.Map;
import com.assignment.event.DefaultParseEvent;
import com.assignment.event.ParseEvent;
import com.assignment.event.handler.EventHandler;
import akka.actor.AbstractActor;

/**
 * The FileParser actor sends different events (“start-of-file”, “line”, “end-of-file”) to an
 * Aggregator actor, depending on the parser state
 */
public class FileParserActor extends AbstractActor {

    private final Map<Class<? extends ParseEvent>, EventHandler<ParseEvent>> eventTypeHandlerMap;

    public FileParserActor(final Map<Class<? extends ParseEvent>, EventHandler<ParseEvent>> eventTypeHandlerMap) {
        this.eventTypeHandlerMap = eventTypeHandlerMap;
    }

    /**
     * A Listener which would receive event from file scanner to parse each file and send events to
     * aggregator actor for further processing.
     */
    @Override
    public Receive createReceive() {
        return receiveBuilder().matchAny(object -> eventTypeHandlerMap.getOrDefault(object.getClass(), eventTypeHandlerMap.get(DefaultParseEvent.class)).process((ParseEvent) object)).build();
    }
}
