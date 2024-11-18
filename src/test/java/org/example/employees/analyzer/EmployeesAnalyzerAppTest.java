package org.example.employees.analyzer;

import org.example.employees.analyzer.exceptions.CsvFileParsingException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.net.URISyntaxException;
import java.nio.file.Paths;
import java.util.Objects;

class EmployeesAnalyzerAppTest {
    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    private final PrintStream originalOut = System.out;

    @BeforeEach
    public void setUpStreams() {
        System.setOut(new PrintStream(outContent));
    }

    @AfterEach
    public void restoreStreams() throws IOException {
        System.setOut(originalOut);
        outContent.close();
    }

    @Test
    void main_success() {
        String fileName;
        try {
            fileName = Paths.get(Objects.requireNonNull(getClass().getClassLoader().getResource("employees.csv")).toURI()).toString();
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }
        Assertions.assertDoesNotThrow(() -> EmployeesAnalyzerApp.main(new String[]{fileName}));
    }

    @Test
    void main_withoutFileName() {
        CsvFileParsingException exception = Assertions.assertThrows(CsvFileParsingException.class, () -> EmployeesAnalyzerApp.main(new String[]{}));
        Assertions.assertEquals("Path to the CSV file should be provided.", exception.getMessage());
    }
}