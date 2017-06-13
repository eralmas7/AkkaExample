package com.assignment.actors;

import java.util.Map;
import com.assignment.event.DefaultScanEvent;
import com.assignment.event.ScanEvent;
import com.assignment.event.handler.EventHandler;
import akka.actor.AbstractActor;

/**
 * The FileScanner actor sends a parse message to a FileParser actor in order to initiate the
 * parsing
 */
public class FileScannerActor extends AbstractActor {

    private final Map<Class<? extends ScanEvent>, EventHandler<ScanEvent>> eventTypeHandlerMap;

    public FileScannerActor(final Map<Class<? extends ScanEvent>, EventHandler<ScanEvent>> eventTypeHandlerMap) {
        this.eventTypeHandlerMap = eventTypeHandlerMap;
    }

    /**
     * A Listener which would receive event from main application to scan each file in a directory
     * given and send events to parser actor for further processing.
     */
    @Override
    public Receive createReceive() {
        return receiveBuilder().matchAny(object -> eventTypeHandlerMap.getOrDefault(object.getClass(), eventTypeHandlerMap.get(DefaultScanEvent.class)).process((ScanEvent) object)).build();
    }
}
