package com.assignment.config;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
import com.assignment.actors.AggregatorActor;
import com.assignment.actors.FileParserActor;
import com.assignment.actors.FileScannerActor;
import com.assignment.actors.lifecycle.ActorSystemLifecycle;
import com.assignment.event.DefaultParseEvent;
import com.assignment.event.DefaultReadEvent;
import com.assignment.event.DefaultScanEvent;
import com.assignment.event.DirectoryScanEvent;
import com.assignment.event.FileEndReadEvent;
import com.assignment.event.FileLineReadEvent;
import com.assignment.event.FileParseEvent;
import com.assignment.event.FileStartReadEvent;
import com.assignment.event.ParseEvent;
import com.assignment.event.ReadEvent;
import com.assignment.event.ScanEvent;
import com.assignment.event.handler.DefaultParseEventHandler;
import com.assignment.event.handler.DefaultReadEventHandler;
import com.assignment.event.handler.DefaultScanEventHandler;
import com.assignment.event.handler.EventHandler;
import com.assignment.event.handler.LineEventHandler;
import com.assignment.event.handler.ParseEventHandler;
import com.assignment.event.handler.ScanEventHandler;
import com.assignment.event.handler.StartEventHandler;
import com.assignment.event.validator.DirectoryCheckValidator;
import com.assignment.event.validator.FileCheckValidator;
import com.assignment.event.validator.FileExistenceValidator;
import com.assignment.event.validator.FilePathValidator;
import com.assignment.event.validator.FilePermissionValidator;
import com.assignment.event.validator.Validator;
import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Props;

/**
 * Configuration for the file word count application.
 */
public abstract class FileWordCountConfig {

    private ActorSystem fileWordCountActorSystem;

    private void injectReadEventHandler(final Map<Class<? extends ReadEvent>, EventHandler<ReadEvent>> readEventHandlerMap, final AtomicInteger counter, final ActorSystemLifecycle actorSystemLifecycle) {
        final EventHandler<ReadEvent> defaultReadEventHandler = new DefaultReadEventHandler();
        final EventHandler<ReadEvent> fileStartEventHandler = new StartEventHandler();
        final Map<String, Long> fileLineCountMap = new ConcurrentHashMap<>();
        final EventHandler<ReadEvent> fileLineReaderEventHandler = new LineEventHandler(fileLineCountMap);
        final EventHandler<ReadEvent> fileEndEventHandler = getEndEventHandler(fileLineCountMap, counter, actorSystemLifecycle);
        readEventHandlerMap.put(DefaultReadEvent.class, defaultReadEventHandler);
        readEventHandlerMap.put(FileStartReadEvent.class, fileStartEventHandler);
        readEventHandlerMap.put(FileEndReadEvent.class, fileEndEventHandler);
        readEventHandlerMap.put(FileLineReadEvent.class, fileLineReaderEventHandler);
    }

    public ActorSystem getActorSystem() {
        return fileWordCountActorSystem;
    }

    public abstract EventHandler<ReadEvent> getEndEventHandler(final Map<String, Long> fileLineCountMap, final AtomicInteger counter, final ActorSystemLifecycle actorSystemLifecycle);

    private void injectScanEventHandler(final Map<Class<? extends ScanEvent>, EventHandler<ScanEvent>> scanEventHandlerMap, final ActorRef parseActor, final ActorRef fileScannerActor, final AtomicInteger counter) {
        final EventHandler<ScanEvent> defaultScanEventHandler = new DefaultScanEventHandler();
        final EventHandler<ScanEvent> scanEventHandler = new ScanEventHandler(fileScannerActor, parseActor, getScanValidator(), counter);
        scanEventHandlerMap.put(DefaultScanEvent.class, defaultScanEventHandler);
        scanEventHandlerMap.put(DirectoryScanEvent.class, scanEventHandler);
    }

    private List<Validator> getParserValidator() {
        final List<Validator> validators = new ArrayList<Validator>(4);
        validators.add(new FilePathValidator());
        validators.add(new FileCheckValidator());
        validators.add(new FileExistenceValidator());
        validators.add(new FilePermissionValidator());
        return validators;
    }

    private List<Validator> getScanValidator() {
        final List<Validator> validators = new ArrayList<Validator>(4);
        validators.add(new FilePathValidator());
        validators.add(new DirectoryCheckValidator());
        validators.add(new FileExistenceValidator());
        validators.add(new FilePermissionValidator());
        return validators;
    }

    private void injectParseEventHandler(final Map<Class<? extends ParseEvent>, EventHandler<ParseEvent>> parseEventHandlerMap, final ActorRef aggregateActor) {
        final EventHandler<ParseEvent> defaultParseEventHandler = new DefaultParseEventHandler();
        final EventHandler<ParseEvent> parseEventHandler = new ParseEventHandler(aggregateActor, getParserValidator());
        parseEventHandlerMap.put(DefaultParseEvent.class, defaultParseEventHandler);
        parseEventHandlerMap.put(FileParseEvent.class, parseEventHandler);
    }

    /**
     * Inject dependencies for actor system.
     * 
     * @return return the actor that is start of pipeline.
     */
    public ActorRef injectDependencies() {
        final Map<Class<? extends ReadEvent>, EventHandler<ReadEvent>> readEventHandler = new HashMap<>();
        final Map<Class<? extends ScanEvent>, EventHandler<ScanEvent>> scanEventHandler = new HashMap<>();
        final Map<Class<? extends ParseEvent>, EventHandler<ParseEvent>> parseEventHandler = new HashMap<>();
        fileWordCountActorSystem = ActorSystem.create("logProcessor");
        final ActorRef aggregatorActor = fileWordCountActorSystem.actorOf(Props.create(AggregatorActor.class, readEventHandler), "aggregatorActor");
        final ActorRef parserActor = fileWordCountActorSystem.actorOf(Props.create(FileParserActor.class, parseEventHandler), "parserActor");
        final ActorRef fileScannerActor = fileWordCountActorSystem.actorOf(Props.create(FileScannerActor.class, scanEventHandler), "fileScannerActor");
        final AtomicInteger counter = new AtomicInteger(0);
        final ActorSystemLifecycle actorSystemLifecycle = new ActorSystemLifecycle(this);
        injectReadEventHandler(readEventHandler, counter, actorSystemLifecycle);
        injectScanEventHandler(scanEventHandler, parserActor, fileScannerActor, counter);
        injectParseEventHandler(parseEventHandler, aggregatorActor);
        return fileScannerActor;
    }
}
