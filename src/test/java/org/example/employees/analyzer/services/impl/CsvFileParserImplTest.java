package org.example.employees.analyzer.services.impl;

import org.example.employees.analyzer.domain.dto.EmployeesDto;
import org.example.employees.analyzer.exceptions.CsvFileParsingException;
import org.example.employees.analyzer.exceptions.LogicalIntegrityException;
import org.example.employees.analyzer.services.CsvFileParser;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.net.URISyntaxException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicReference;

class CsvFileParserImplTest {
    private final CsvFileParser fileParser;

    CsvFileParserImplTest() {
        this.fileParser = new CsvFileParserImpl(new EmployeeDtoValidatorImpl(), new EmployeeFieldsParserImpl());
    }

    @Test
    void parse() {
        Path filePath;
        try {
            filePath = Paths.get(Objects.requireNonNull(getClass().getClassLoader().getResource("employees.csv")).toURI());
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }
        AtomicReference<EmployeesDto> employeesDtoAtomic = new AtomicReference<>();
        Assertions.assertDoesNotThrow(() -> employeesDtoAtomic.set(fileParser.parse(filePath.toString())));
        Assertions.assertEquals(10, employeesDtoAtomic.get().getEmployeesIds().size());
    }

    @Test
    void parse_withWrongFilePath() {
        Assertions.assertThrows(CsvFileParsingException.class, () -> fileParser.parse("wrongPath.csv"));
    }

    @Test
    void parse_withTheSameIdAndManagerId() {
        String filePath;
        try {
            filePath = Paths.get(Objects.requireNonNull(getClass().getClassLoader().getResource("employees_withTheSameIdAndManagerId.csv")).toURI()).toString();
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }
        Assertions.assertThrows(LogicalIntegrityException.class, () -> fileParser.parse(filePath));
    }

    @Test
    void parse_withWrongDelimiter() {
        String filePath;
        try {
            filePath = Paths.get(Objects.requireNonNull(getClass().getClassLoader().getResource("employees_withWrongDelimiter.csv")).toURI()).toString();
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }
        Assertions.assertThrows(CsvFileParsingException.class, () -> fileParser.parse(filePath));
    }

    @Test
    void parse_withThreeFields() {
        String filePath;
        try {
            filePath = Paths.get(Objects.requireNonNull(getClass().getClassLoader().getResource("employees_withThreeFields.csv")).toURI()).toString();
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }
        Assertions.assertThrows(LogicalIntegrityException.class, () -> fileParser.parse(filePath));
    }

    @Test
    void parse_withEmptyLine() {
        String filePath;
        try {
            filePath = Paths.get(Objects.requireNonNull(getClass().getClassLoader().getResource("employees_withEmptyLine.csv")).toURI()).toString();
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }
        CsvFileParsingException exception = Assertions.assertThrows(CsvFileParsingException.class, () -> fileParser.parse(filePath));
        Assertions.assertEquals("CSV file parsing error: Line from file is empty", exception.getMessage());
    }
}