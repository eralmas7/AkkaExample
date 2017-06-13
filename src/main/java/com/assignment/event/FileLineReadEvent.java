package com.assignment.event;

import akka.actor.ActorRef;

/**
 * Line read event from parser to process a line present in the file resource.
 */
public class FileLineReadEvent implements ReadEvent {

    private final String path;
    private final String line;
    private final ActorRef actorRef;

    public FileLineReadEvent(final String path, final String line, final ActorRef actorRef) {
        this.path = path;
        this.actorRef = actorRef;
        this.line = line;
    }

    @Override
    public String getPath() {
        return path;
    }

    @Override
    public ActorRef getActorRef() {
        return actorRef;
    }

    public String getLine() {
        return line;
    }
}
