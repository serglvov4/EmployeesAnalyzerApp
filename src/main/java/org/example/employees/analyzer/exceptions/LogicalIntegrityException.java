package org.example.employees.analyzer.exceptions;

/**
 * Logical integration exception. Occurs when employee data is inconsistent
 */
public class LogicalIntegrityException extends RuntimeException {
    public LogicalIntegrityException(String message) {
        super(message);
    }
}
