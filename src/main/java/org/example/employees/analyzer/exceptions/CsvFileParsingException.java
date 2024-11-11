package org.example.employees.analyzer.exceptions;

/**
 * CSV file parsing exception
 */
public class CsvFileParsingException extends RuntimeException {
    public CsvFileParsingException(String message) {
        super(message);
    }
}
