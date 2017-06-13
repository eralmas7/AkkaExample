package com.assignment.main;

import static java.util.stream.Collectors.toMap;
import static org.assertj.core.api.Assertions.assertThat;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Map;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import com.assignment.config.FileWordCountConfig;
import com.assignment.config.FileWordCountFileConfig;
import com.assignment.event.DirectoryScanEvent;
import akka.actor.ActorRef;

/*
 * Integration test to prove application works.
 */
public class FileWordCountAppTest {

    private static final String outputFilePath = System.getProperty("user.home") + "/test.txt";
    private ActorRef fileScannerActor;

    @Before
    public void setUp() throws Exception {
        System.setProperty("assignment.result.file.path", outputFilePath);
        final FileWordCountConfig fileWordCountConfig = new FileWordCountFileConfig();
        fileScannerActor = fileWordCountConfig.injectDependencies();
    }

    @Test
    public void givenValidDirectory_whenPipelineStarts_writeToFileSuccessfully() throws Exception {
        // Arrange
        final String directoryToScan = "src/test/resources";
        // Act
        fileScannerActor.tell(new DirectoryScanEvent(directoryToScan), ActorRef.noSender());
        waitFor(500);
        // Assert
        Map<String, Integer> fileLines = Files.readAllLines(Paths.get(outputFilePath)).stream().map(line -> line.split(",")).collect(toMap(array -> array[0], array -> Integer.parseInt(array[1], 10)));
        assertThat(fileLines.get("HelloWorld.txt")).isEqualTo(2);
        assertThat(fileLines.get("HiWorld.txt")).isEqualTo(4);
    }

    private void waitFor(final long time) throws InterruptedException {
        Thread.sleep(time);
    }

    @After
    public void tearDown() throws Exception {
        Files.delete(Paths.get(outputFilePath));
    }
}
