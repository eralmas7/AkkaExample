A small Java application that processes log files, using a few different actors, showing the usage of message parsing and the Akka framework basics.

This is what the application does:
- On application startup (main), create ActorSystem and eventual actors.
- The application (main), sends a scan message to a FileScanner actor which will check if there is any file in predefined directory
- The FileScanner actor then sends a parse message to a FileParser actor in order to initiate the parsing
- The FileParser actor sends different events (“start-of-file”, “line”, “end-of-file”) to an Aggregator actor, depending on the parser state
- The Aggregator actor split words in the lines by the space “ “ character based on the “line” event
- The Aggregator actor counts the number of words in a file
- The Aggregator actor prints the number of words in a file in the console when it receives the “end-of-file” event
