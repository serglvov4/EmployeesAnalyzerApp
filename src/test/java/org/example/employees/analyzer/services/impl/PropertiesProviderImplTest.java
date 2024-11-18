package org.example.employees.analyzer.services.impl;

import org.example.employees.analyzer.TestLoggerHandler;
import org.example.employees.analyzer.services.PropertiesProvider;
import org.junit.jupiter.api.*;

import java.math.BigDecimal;
import java.util.logging.Logger;

class PropertiesProviderImplTest {
    private static final BigDecimal DEFAULT_MINIMAL_SALARY_RATE_LIMIT = new BigDecimal("1.2");
    private static final BigDecimal DEFAULT_MAXIMAL_SALARY_RATE_LIMIT = new BigDecimal("1.5");
    private static final Integer DEFAULT_MAXIMAL_REPORTING_LINE_LENGTH = 4;

    private static final String LOG_ERROR_MESSAGE = "Default values applied. Can't read application properties";
    private static final String WRONG_PROPERTIES_FILE_NAME = "/wrong.properties";
    private static final String EMPTY_PROPERTIES_FILE_NAME = "/empty.properties";
    private static final String INCORRECT_PROPERTIES_FILE_NAME = "/incorrect.properties";

    private static final TestLoggerHandler handler = new TestLoggerHandler();
    private static final Logger logger = Logger.getLogger(PropertiesProviderImpl.class.getName());

    @BeforeAll
    public static void setUpLoggerHandler() {
        logger.setUseParentHandlers(false);
        logger.addHandler(handler);
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
    void getMinimalSalaryRateLimit() {
        PropertiesProvider propertiesProviderWithCorrectPropertiesFile = new PropertiesProviderImpl();
        Assertions.assertEquals(DEFAULT_MINIMAL_SALARY_RATE_LIMIT,
                propertiesProviderWithCorrectPropertiesFile.getMinimalSalaryRateLimit());
        Assertions.assertEquals(0, handler.getMessagesCount());
    }

    @Test
    void getMaximalSalaryRateLimit() {
        PropertiesProvider propertiesProviderWithCorrectPropertiesFile = new PropertiesProviderImpl();
        Assertions.assertEquals(DEFAULT_MAXIMAL_SALARY_RATE_LIMIT,
                propertiesProviderWithCorrectPropertiesFile.getMaximalSalaryRateLimit());
        Assertions.assertEquals(0, handler.getMessagesCount());
    }

    @Test
    void getMaximalReportingLineLength() {
        PropertiesProvider propertiesProviderWithCorrectPropertiesFile = new PropertiesProviderImpl();
        Assertions.assertEquals(DEFAULT_MAXIMAL_REPORTING_LINE_LENGTH,
                propertiesProviderWithCorrectPropertiesFile.getMaximalReportingLineLength());
        Assertions.assertEquals(0, handler.getMessagesCount());
    }

    @Test
    void getMinimalSalaryRateLimit_withWrongPropertiesFileName() {
        PropertiesProvider propertiesProviderWithWrongPropertiesFile = new PropertiesProviderImpl(WRONG_PROPERTIES_FILE_NAME);
        Assertions.assertEquals(DEFAULT_MINIMAL_SALARY_RATE_LIMIT,
                propertiesProviderWithWrongPropertiesFile.getMinimalSalaryRateLimit());
        Assertions.assertTrue(handler.checkMessage(LOG_ERROR_MESSAGE));
    }

    @Test
    void getMaximalSalaryRateLimit_withWrongPropertiesFileName() {
        PropertiesProvider propertiesProviderWithWrongPropertiesFile = new PropertiesProviderImpl(WRONG_PROPERTIES_FILE_NAME);
        Assertions.assertEquals(DEFAULT_MAXIMAL_SALARY_RATE_LIMIT,
                propertiesProviderWithWrongPropertiesFile.getMaximalSalaryRateLimit());
        Assertions.assertTrue(handler.checkMessage(LOG_ERROR_MESSAGE));
    }

    @Test
    void getMaximalReportingLineLength_withWrongPropertiesFileName() {
        PropertiesProvider propertiesProviderWithWrongPropertiesFile = new PropertiesProviderImpl(WRONG_PROPERTIES_FILE_NAME);
        Assertions.assertEquals(DEFAULT_MAXIMAL_REPORTING_LINE_LENGTH,
                propertiesProviderWithWrongPropertiesFile.getMaximalReportingLineLength());
        Assertions.assertTrue(handler.checkMessage(LOG_ERROR_MESSAGE));
    }

    @Test
    void getMinimalSalaryRateLimit_withEmptyPropertiesFile() {
        PropertiesProvider propertiesProviderWithWrongPropertiesFile = new PropertiesProviderImpl(EMPTY_PROPERTIES_FILE_NAME);
        Assertions.assertEquals(DEFAULT_MINIMAL_SALARY_RATE_LIMIT,
                propertiesProviderWithWrongPropertiesFile.getMinimalSalaryRateLimit());
        Assertions.assertTrue(handler.checkMessage("Can't find value for property app.minimal-salary-rate-limit. Default value applied."));
    }

    @Test
    void getMaximalSalaryRateLimit_withEmptyPropertiesFile() {
        PropertiesProvider propertiesProviderWithWrongPropertiesFile = new PropertiesProviderImpl(EMPTY_PROPERTIES_FILE_NAME);
        Assertions.assertEquals(DEFAULT_MAXIMAL_SALARY_RATE_LIMIT,
                propertiesProviderWithWrongPropertiesFile.getMaximalSalaryRateLimit());
        Assertions.assertTrue(handler.checkMessage("Can't find value for property app.maximal-salary-rate-limit. Default value applied."));
    }

    @Test
    void getMaximalReportingLineLength_withEmptyPropertiesFile() {
        PropertiesProvider propertiesProviderWithWrongPropertiesFile = new PropertiesProviderImpl(EMPTY_PROPERTIES_FILE_NAME);
        Assertions.assertEquals(DEFAULT_MAXIMAL_REPORTING_LINE_LENGTH,
                propertiesProviderWithWrongPropertiesFile.getMaximalReportingLineLength());
        Assertions.assertTrue(handler.checkMessage("Can't find value for property app.maximal-reporting-line-length. Default value applied."));
    }

    @Test
    void getMinimalSalaryRateLimit_withIncorrectPropertiesFile() {
        PropertiesProvider propertiesProviderWithWrongPropertiesFile = new PropertiesProviderImpl(INCORRECT_PROPERTIES_FILE_NAME);
        Assertions.assertEquals(DEFAULT_MINIMAL_SALARY_RATE_LIMIT,
                propertiesProviderWithWrongPropertiesFile.getMinimalSalaryRateLimit());
        Assertions.assertTrue(handler.checkMessage("Invalid value for property app.minimal-salary-rate-limit (HJ1.2). Default value applied."));
    }

    @Test
    void getMaximalSalaryRateLimit_withIncorrectPropertiesFile() {
        PropertiesProvider propertiesProviderWithWrongPropertiesFile = new PropertiesProviderImpl(INCORRECT_PROPERTIES_FILE_NAME);
        Assertions.assertEquals(DEFAULT_MAXIMAL_SALARY_RATE_LIMIT,
                propertiesProviderWithWrongPropertiesFile.getMaximalSalaryRateLimit());
        Assertions.assertTrue(handler.checkMessage("Invalid value for property app.maximal-salary-rate-limit (1.5AC). Default value applied."));
    }

    @Test
    void getMaximalReportingLineLength_withIncorrectPropertiesFile() {
        PropertiesProvider propertiesProviderWithWrongPropertiesFile = new PropertiesProviderImpl(INCORRECT_PROPERTIES_FILE_NAME);
        Assertions.assertEquals(DEFAULT_MAXIMAL_REPORTING_LINE_LENGTH,
                propertiesProviderWithWrongPropertiesFile.getMaximalReportingLineLength());
        Assertions.assertTrue(handler.checkMessage("Invalid value for property app.maximal-reporting-line-length (T4). Default value applied."));
    }
}