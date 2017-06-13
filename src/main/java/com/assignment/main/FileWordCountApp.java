package com.assignment.main;

import com.assignment.actors.lifecycle.ActorSystemLifecycle;
import com.assignment.config.FileWordCountConfig;
import com.assignment.config.FileWordCountConsoleConfig;

/**
 * Starting point of the application who delegates all configuration and dependency injection
 * responsibility to configuration class.
 */
public class FileWordCountApp {

    /**
     * Entry point of the application
     * 
     * @param commandLineArgs
     */
    public static void main(final String[] commandLineArgs) {
        String directoryToScan = "/tmp";
        if (commandLineArgs.length == 0) {
            System.out.println("You have not specified any path to scan. Will be using pre defined path /tmp for now");
            System.out.println("Usage is: java -jar AkkaExample-1.0.jar <directorypath>");
        } else {
            directoryToScan = commandLineArgs[0];
        }
        final FileWordCountConfig fileWordCountConfig = new FileWordCountConsoleConfig();
        final ActorSystemLifecycle actorSystemLifecycle = new ActorSystemLifecycle(fileWordCountConfig);
        actorSystemLifecycle.startPipeline(directoryToScan);
    }
}
