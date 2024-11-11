package org.example.employees.analyzer.services.impl;

import org.example.employees.analyzer.services.PropertiesProvider;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.math.BigDecimal;

class PropertiesProviderImplTest {
    private final ByteArrayOutputStream errContent = new ByteArrayOutputStream();
    private final PrintStream originalErr = System.err;

    @BeforeEach
    public void setUpStreams() {
        System.setErr(new PrintStream(errContent));
    }

    @AfterEach
    public void restoreStreams() {
        System.setErr(originalErr);
    }

    @Test
    void getMinimalSalaryRateLimit() {
        PropertiesProvider propertiesProviderWithCorrectPropertiesFile = new PropertiesProviderImpl();
        Assertions.assertEquals(new BigDecimal("1.2"),
                propertiesProviderWithCorrectPropertiesFile.getMinimalSalaryRateLimit());
        Assertions.assertEquals("", errContent.toString());
    }

    @Test
    void getMaximalSalaryRateLimit() {
        PropertiesProvider propertiesProviderWithCorrectPropertiesFile = new PropertiesProviderImpl();
        Assertions.assertEquals(new BigDecimal("1.5"),
                propertiesProviderWithCorrectPropertiesFile.getMaximalSalaryRateLimit());
        Assertions.assertEquals("", errContent.toString());
    }

    @Test
    void getMaximalReportingLineLength() {
        PropertiesProvider propertiesProviderWithCorrectPropertiesFile = new PropertiesProviderImpl();
        Assertions.assertEquals(4,
                propertiesProviderWithCorrectPropertiesFile.getMaximalReportingLineLength());
        Assertions.assertEquals("", errContent.toString());
    }

    @Test
    void getMinimalSalaryRateLimit_withWrongPropertiesFile() {
        PropertiesProvider propertiesProviderWithWrongPropertiesFile = new PropertiesProviderImpl("wrong.properties");
        Assertions.assertEquals(new BigDecimal("1.2"),
                propertiesProviderWithWrongPropertiesFile.getMinimalSalaryRateLimit());
        Assertions.assertEquals("Default values applied. Can't read application properties" + System.lineSeparator(),
                errContent.toString());
    }

    @Test
    void getMaximalSalaryRateLimit_withWrongPropertiesFile() {
        PropertiesProvider propertiesProviderWithWrongPropertiesFile = new PropertiesProviderImpl("wrong.properties");
        Assertions.assertEquals(new BigDecimal("1.5"),
                propertiesProviderWithWrongPropertiesFile.getMaximalSalaryRateLimit());
        Assertions.assertEquals("Default values applied. Can't read application properties" + System.lineSeparator(),
                errContent.toString());
    }

    @Test
    void getMaximalReportingLineLength_withWrongPropertiesFile() {
        PropertiesProvider propertiesProviderWithWrongPropertiesFile = new PropertiesProviderImpl("wrong.properties");
        Assertions.assertEquals(4,
                propertiesProviderWithWrongPropertiesFile.getMaximalReportingLineLength());
        Assertions.assertEquals("Default values applied. Can't read application properties" + System.lineSeparator(),
                errContent.toString());
    }
}