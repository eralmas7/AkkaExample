package com.assignment.event.handler;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.assignment.event.FileEndReadEvent;
import com.assignment.event.FileLineReadEvent;
import com.assignment.event.FileStartReadEvent;
import com.assignment.event.ParseEvent;
import com.assignment.event.validator.Validator;
import akka.actor.ActorRef;

/**
 * An event event passed to Parser.
 */
public class ParseEventHandler implements EventHandler<ParseEvent> {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    private final ActorRef aggregatorActor;
    private final List<Validator> validatorList;

    public ParseEventHandler(final ActorRef aggregatorActor, final List<Validator> validatorList) {
        this.aggregatorActor = aggregatorActor;
        this.validatorList = validatorList;
    }

    private void validatePath(final String path) {
        for (Validator validator : validatorList) {
            validator.validate(path);
        }
    }

    /**
     * Tell aggregator actor the following: We are about to start reading the file. We have to then
     * processing the file line by line. We have to log the word count in that file.
     */
    @Override
    public void process(final ParseEvent parseEvent) {
        logger.info("Parse File : " + parseEvent.getPath());
        try {
            validatePath(parseEvent.getPath());
            final Path file = Paths.get(parseEvent.getPath());
            aggregatorActor.tell(new FileStartReadEvent(parseEvent.getPath(), aggregatorActor), ActorRef.noSender());
            Files.lines(file, StandardCharsets.UTF_8).forEach(line -> aggregatorActor.tell(new FileLineReadEvent(parseEvent.getPath(), line, aggregatorActor), ActorRef.noSender()));
            aggregatorActor.tell(new FileEndReadEvent(parseEvent.getPath(), aggregatorActor), ActorRef.noSender());
        } catch (IOException e) {
            logger.error("Got IO Exception while reading file {}", parseEvent.getPath());// No-op
        }
    }
}
