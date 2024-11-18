package org.example.employees.analyzer;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.LogRecord;
import java.util.logging.StreamHandler;

/**
 * Logging handler for tests
 */
public class TestLoggerHandler extends StreamHandler {
    private final List<String> messages = new ArrayList<>();

    /**
     * Publish a {@code LogRecord}.
     * <p>
     * The logging request was made initially to a {@code Logger} object,
     * which initialized the {@code LogRecord} and forwarded it here.
     * <p>
     * The {@code Handler}  is responsible for formatting the message, when and
     * if necessary.  The formatting should include localization.
     *
     * @param logRecord description of the log event. A null record is
     *               silently ignored and is not published
     */
    @Override
    public void publish(LogRecord logRecord) {
        messages.add(getFormatter().format(logRecord));
    }

    /**
     * Check if message exists
     * @param message Message
     * @return True if message exists
     */
    public boolean checkMessage(String message) {
        return messages.stream().anyMatch(s -> s.contains(message));
    }

    /**
     * Clear log messages
     */
    public void clearMessages() {
        messages.clear();
    }

    /**
     * Get count of messages
     * @return Count of messages
     */
    public int getMessagesCount() {
        return messages.size();
    }
}
