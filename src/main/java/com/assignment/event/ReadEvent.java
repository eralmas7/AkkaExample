package com.assignment.event;

import akka.actor.ActorRef;

/**
 * An event for an aggregator actor.
 */
public interface ReadEvent {

    /**
     * Returns the path of the file resource.
     * 
     * @return
     */
    public String getPath();

    /**
     * Get the reference of the actor which is processing event.
     * 
     * @return
     */
    public ActorRef getActorRef();
}
