package org.example.employees.analyzer.services.impl;

import org.example.employees.analyzer.exceptions.CsvFileParsingException;
import org.example.employees.analyzer.services.EmployeeFieldsParser;

import java.math.BigDecimal;
import java.util.Objects;

/**
 * Validate and parse fields string values from CSV file
 */
public class EmployeeFieldsParserImpl implements EmployeeFieldsParser {
    private static final String EMPTY_VALUE_MESSAGE = "Empty value of field";

    /**
     * Validate and parse Employee ID value from CSV file
     * @param employeeId Employee ID string value
     * @return Employee ID
     */
    @Override
    public Integer parseEmployeeId(String employeeId) {
        if (Objects.isNull(employeeId) || employeeId.isBlank()) {
            throw getParsingException("Employee Id", EMPTY_VALUE_MESSAGE);
        }
        try {
            return Integer.parseInt(employeeId);
        } catch (NumberFormatException exception) {
            throw getParsingException("Employee Id", exception.getMessage());
        }
    }

    /**
     * Validate and parse First Name value from CSV file
     * @param firstName First Name string value
     * @return First Name
     */
    @Override
    public String parseFirstName(String firstName) {
        if (Objects.isNull(firstName) || firstName.isBlank()) {
            throw getParsingException("First Name", EMPTY_VALUE_MESSAGE);
        }
        return firstName.trim();
    }

    /**
     * Validate and parse Last Name value from CSV file
     * @param lastName Last Name string value
     * @return Last Name
     */
    @Override
    public String parseLastName(String lastName) {
        if (Objects.isNull(lastName) || lastName.isBlank()) {
            throw getParsingException("Last Name", EMPTY_VALUE_MESSAGE);
        }
        return lastName.trim();
    }

    /**
     * Validate and parse Salary value from CSV file
     * @param salary Salary string value
     * @return Salary
     */
    @Override
    public BigDecimal parseSalary(String salary) {
        if (Objects.isNull(salary) || salary.isBlank()) {
            throw getParsingException("Salary", EMPTY_VALUE_MESSAGE);
        }
        try {
            return new BigDecimal(salary);
        } catch (NumberFormatException exception) {
            throw getParsingException("Salary", exception.getMessage());
        }
    }

    /**
     * Validate and parse Manager ID value from CSV file
     * @param managerId Manager ID string value
     * @return Manager ID
     */
    @Override
    public Integer parseManagerId(String managerId) {
        if (Objects.isNull(managerId) || managerId.isBlank()) {
            throw getParsingException("Manager Id", EMPTY_VALUE_MESSAGE);
        }
        try {
            return Integer.parseInt(managerId);
        } catch (NumberFormatException exception) {
            throw getParsingException("Manager Id", exception.getMessage());
        }
    }

    /**
     * Create CSV file parsing exception
     * @param fieldName Name of field
     * @param exceptionMessage Message with details about exception's cause
     * @return CSV file parsing exception
     */
    private CsvFileParsingException getParsingException(String fieldName, String exceptionMessage) {
        return new CsvFileParsingException("Invalid data format for the \"" + fieldName + "\" field. " + exceptionMessage);
    }
}
