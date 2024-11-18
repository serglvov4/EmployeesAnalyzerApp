package org.example.employees.analyzer.services.impl;

import org.example.employees.analyzer.exceptions.CsvFileParsingException;
import org.example.employees.analyzer.services.EmployeeFieldsParser;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullSource;
import org.junit.jupiter.params.provider.ValueSource;

import java.math.BigDecimal;

class EmployeeFieldsParserImplTest {
    private final EmployeeFieldsParser employeeFieldsParser = new EmployeeFieldsParserImpl();

    @Test
    void parseEmployeeId_success() {
        String employeeIdValue = "123";
        Integer employeeId = Assertions.assertDoesNotThrow(() -> employeeFieldsParser.parseEmployeeId(employeeIdValue));
        Assertions.assertEquals(123, employeeId);
    }

    @ParameterizedTest
    @NullSource
    void parseEmployeeId_withNullEmployeeId(String employeeIdValue) {
        Assertions.assertThrows(CsvFileParsingException.class, () -> employeeFieldsParser.parseEmployeeId(employeeIdValue));
    }

    @ParameterizedTest
    @ValueSource(strings = {"   ", "", "A123"})
    void parseEmployeeId_withCsvFileParsingException(String employeeIdValue) {
        Assertions.assertThrows(CsvFileParsingException.class, () -> employeeFieldsParser.parseEmployeeId(employeeIdValue));
    }

    @Test
    void parseFirstName_success() {
        String firstNameValue = "Joe";
        String firstName = Assertions.assertDoesNotThrow(() -> employeeFieldsParser.parseFirstName(firstNameValue));
        Assertions.assertEquals("Joe", firstName);
    }

    @ParameterizedTest
    @NullSource
    void parseFirstName_withNullFirstName(String firstNameValue) {
        Assertions.assertThrows(CsvFileParsingException.class, () -> employeeFieldsParser.parseFirstName(firstNameValue));
    }

    @ParameterizedTest
    @ValueSource(strings = {"   ", ""})
    void parseFirstName_withCsvFileParsingException(String firstNameValue) {
        Assertions.assertThrows(CsvFileParsingException.class, () -> employeeFieldsParser.parseFirstName(firstNameValue));
    }

    @Test
    void parseLastName_success() {
        String lastNameValue = "Doe";
        String lastName = Assertions.assertDoesNotThrow(() -> employeeFieldsParser.parseLastName(lastNameValue));
        Assertions.assertEquals("Doe", lastName);
    }

    @ParameterizedTest
    @NullSource
    void parseLastName_withNullLastName(String lastNameValue) {
        Assertions.assertThrows(CsvFileParsingException.class, () -> employeeFieldsParser.parseLastName(lastNameValue));
    }

    @ParameterizedTest
    @ValueSource(strings = {"   ", ""})
    void parseLastName_withCsvFileParsingException(String lastNameValue) {
        Assertions.assertThrows(CsvFileParsingException.class, () -> employeeFieldsParser.parseLastName(lastNameValue));
    }

    @Test
    void parseSalary_success() {
        String salaryValue = "50000";
        BigDecimal salary = Assertions.assertDoesNotThrow(() -> employeeFieldsParser.parseSalary(salaryValue));
        Assertions.assertEquals(new BigDecimal("50000"), salary);
    }

    @ParameterizedTest
    @NullSource
    void parseSalary_withNullSalary(String salaryValue) {
        Assertions.assertThrows(CsvFileParsingException.class, () -> employeeFieldsParser.parseSalary(salaryValue));
    }

    @ParameterizedTest
    @ValueSource(strings = {"   ", "", "A45000"})
    void parseSalary_withCsvFileParsingException(String salaryValue) {
        Assertions.assertThrows(CsvFileParsingException.class, () -> employeeFieldsParser.parseSalary(salaryValue));
    }

    @Test
    void parseManagerId_success() {
        String managerIdValue = "123";
        Integer managerId = Assertions.assertDoesNotThrow(() -> employeeFieldsParser.parseManagerId(managerIdValue));
        Assertions.assertEquals(123, managerId);
    }

    @ParameterizedTest
    @NullSource
    void parseManagerId_withNullEmployeeId(String managerIdValue) {
        Assertions.assertThrows(CsvFileParsingException.class, () -> employeeFieldsParser.parseManagerId(managerIdValue));
    }

    @ParameterizedTest
    @ValueSource(strings = {"   ", "", "A123"})
    void parseManagerId_withCsvFileParsingException(String managerIdValue) {
        Assertions.assertThrows(CsvFileParsingException.class, () -> employeeFieldsParser.parseManagerId(managerIdValue));
    }
}