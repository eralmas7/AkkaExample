package com.assignment.event.handler;

import java.io.File;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.assignment.event.FileParseEvent;
import com.assignment.event.ScanEvent;
import com.assignment.event.validator.Validator;
import akka.actor.ActorRef;
import akka.actor.PoisonPill;

/**
 * An event event passed to File scanner.
 */
public class ScanEventHandler implements EventHandler<ScanEvent> {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    private final ActorRef parserActor;
    private final ActorRef fileScannerActor;
    private final List<Validator> validatorList;
    private final AtomicInteger counter;

    public ScanEventHandler(final ActorRef fileScannerActor, final ActorRef parserActor, final List<Validator> validatorList, final AtomicInteger counter) {
        this.parserActor = parserActor;
        this.validatorList = validatorList;
        this.fileScannerActor = fileScannerActor;
        this.counter = counter;
    }

    private void cleanUp() {
        parserActor.tell(PoisonPill.getInstance(), ActorRef.noSender());
        fileScannerActor.tell(PoisonPill.getInstance(), ActorRef.noSender());
    }

    private void validatePath(final String path) {
        for (Validator validator : validatorList) {
            validator.validate(path);
        }
    }

    /**
     * Ask parser to process each file in a directory given by the scan event.
     */
    @Override
    public void process(final ScanEvent scanEvent) {
        try {
            logger.info("About to scan directory: " + scanEvent.getPath());
            validatePath(scanEvent.getPath());
            final File directory = new File(scanEvent.getPath());
            final List<File> filesToProcess = Arrays.stream(directory.listFiles()).filter(file -> file.isFile()).collect(Collectors.toList());
            counter.set(filesToProcess.size());
            filesToProcess.forEach(file -> parserActor.tell(new FileParseEvent(file.getAbsolutePath()), ActorRef.noSender()));
        } finally {
            cleanUp();
        }
    }
}
