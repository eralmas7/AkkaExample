package com.assignment.event;

import akka.actor.ActorRef;

/**
 * Start event from parser to start processing the file resource.
 */
public class FileStartReadEvent implements ReadEvent {

    private final String path;
    private final ActorRef actorRef;

    public FileStartReadEvent(final String path, final ActorRef actorRef) {
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
