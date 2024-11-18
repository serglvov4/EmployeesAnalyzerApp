package org.example.employees.analyzer;

import org.junit.jupiter.api.*;

import java.util.logging.Logger;

class EmployeesAnalyzerRunnerTest {
    private static final TestLoggerHandler handler = new TestLoggerHandler();
    private static final Logger logger = Logger.getLogger(EmployeesAnalyzerRunner.class.getName());

    @BeforeAll
    public static void setUpLoggerHandler() {
        logger.addHandler(handler);
        logger.setUseParentHandlers(false);
    }

    @BeforeEach
    void clearLogMessages() {
        handler.clearMessages();
    }

    @AfterAll
    public static void removeLoggerHandler() {
        logger.removeHandler(handler);
        logger.setUseParentHandlers(true);
    }

    @Test
    void run() {
        Runnable runner = new EmployeesAnalyzerRunner("wrongFileName");
        Assertions.assertDoesNotThrow(runner::run);
        Assertions.assertTrue(handler.checkMessage("CSV file parsing error: wrongFileName"));
    }
}