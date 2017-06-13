package com.assignment.actors.lifecycle;

import com.assignment.config.FileWordCountConfig;
import com.assignment.event.DirectoryScanEvent;
import akka.actor.ActorRef;

public class ActorSystemLifecycle {

    private final FileWordCountConfig fileWordCountConfig;

    public ActorSystemLifecycle(final FileWordCountConfig fileWordCountConfig) {
        this.fileWordCountConfig = fileWordCountConfig;
    }

    /**
     * Inject dependencies followed by starting the pipeline of event stream.
     * 
     * @param directoryToScan
     */
    public void startPipeline(final String directoryToScan) {
        final ActorRef fileScannerActor = fileWordCountConfig.injectDependencies();
        fileScannerActor.tell(new DirectoryScanEvent(directoryToScan), ActorRef.noSender());
    }

    /**
     * Shutdown the entire actor system.
     */
    public void stopPipeline() {
        fileWordCountConfig.getActorSystem().terminate();
    }
}
