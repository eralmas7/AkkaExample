package com.assignment.event;

import akka.actor.ActorRef;

/**
 * End event from parser to stop processing the file resource.
 */
public class FileEndReadEvent implements ReadEvent {

    private final String path;
    private final ActorRef actorRef;

    public FileEndReadEvent(final String path, final ActorRef actorRef) {
        this.path = path;
        this.actorRef = actorRef;
    }

    @Override
    public String getPath() {
        return path;
    }

    @Override
    public ActorRef getActorRef() {
        return actorRef;
    }
}
