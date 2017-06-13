package com.assignment.event;

import akka.actor.ActorRef;

/**
 * An unknown event from parser.
 */
public class DefaultReadEvent implements ReadEvent {

    @Override
    public String getPath() {
        throw new UnsupportedOperationException("This method is not supported");
    }

    @Override
    public ActorRef getActorRef() {
        throw new UnsupportedOperationException("This method is not supported");
    }
}
