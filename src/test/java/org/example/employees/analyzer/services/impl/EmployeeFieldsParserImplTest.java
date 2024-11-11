package org.example.employees.analyzer.services.impl;

import org.example.employees.analyzer.exceptions.CsvFileParsingException;
import org.example.employees.analyzer.services.EmployeeFieldsParser;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

class EmployeeFieldsParserImplTest {
    private final EmployeeFieldsParser employeeFieldsParser = new EmployeeFieldsParserImpl();

    @Test
    void parseEmployeeId_success() {
        String employeeIdValue = "123";
        Integer employeeId = Assertions.assertDoesNotThrow(() -> employeeFieldsParser.parseEmployeeId(employeeIdValue));
        Assertions.assertEquals(123, employeeId);
    }

    @Test
    void parseEmployeeId_withNullEmployeeId() {
        String employeeIdValue = null;
        Assertions.assertThrows(CsvFileParsingException.class, () -> employeeFieldsParser.parseEmployeeId(employeeIdValue));
    }

    @Test
    void parseEmployeeId_withBlankEmployeeId() {
        String employeeIdValue = "   ";
        Assertions.assertThrows(CsvFileParsingException.class, () -> employeeFieldsParser.parseEmployeeId(employeeIdValue));
    }

    @Test
    void parseEmployeeId_withEmptyEmployeeId() {
        String employeeIdValue = "";
        Assertions.assertThrows(CsvFileParsingException.class, () -> employeeFieldsParser.parseEmployeeId(employeeIdValue));
    }

    @Test
    void parseEmployeeId_withWrongFormatEmployeeId() {
        String employeeIdValue = "A123";
        Assertions.assertThrows(CsvFileParsingException.class, () -> employeeFieldsParser.parseEmployeeId(employeeIdValue));
    }

    @Test
    void parseFirstName_success() {
        String firstNameValue = "Joe";
        String firstName = Assertions.assertDoesNotThrow(() -> employeeFieldsParser.parseFirstName(firstNameValue));
        Assertions.assertEquals("Joe", firstName);
    }

    @Test
    void parseFirstName_withNullFirstName() {
        String firstNameValue = null;
        Assertions.assertThrows(CsvFileParsingException.class, () -> employeeFieldsParser.parseFirstName(firstNameValue));
    }

    @Test
    void parseFirstName_withBlankFirstName() {
        String firstNameValue = "   ";
        Assertions.assertThrows(CsvFileParsingException.class, () -> employeeFieldsParser.parseFirstName(firstNameValue));
    }

    @Test
    void parseFirstName_withEmptyFirstName() {
        String firstNameValue = "";
        Assertions.assertThrows(CsvFileParsingException.class, () -> employeeFieldsParser.parseFirstName(firstNameValue));
    }

    @Test
    void parseLastName_success() {
        String lastNameValue = "Doe";
        String lastName = Assertions.assertDoesNotThrow(() -> employeeFieldsParser.parseLastName(lastNameValue));
        Assertions.assertEquals("Doe", lastName);
    }

    @Test
    void parseLastName_withNullLastName() {
        String lastNameValue = null;
        Assertions.assertThrows(CsvFileParsingException.class, () -> employeeFieldsParser.parseLastName(lastNameValue));
    }

    @Test
    void parseLastName_withBlankLastName() {
        String lastNameValue = "   ";
        Assertions.assertThrows(CsvFileParsingException.class, () -> employeeFieldsParser.parseLastName(lastNameValue));
    }

    @Test
    void parseLastName_withEmptyLastName() {
        String lastNameValue = "";
        Assertions.assertThrows(CsvFileParsingException.class, () -> employeeFieldsParser.parseLastName(lastNameValue));
    }

    @Test
    void parseSalary_success() {
        String salaryValue = "50000";
        BigDecimal salary = Assertions.assertDoesNotThrow(() -> employeeFieldsParser.parseSalary(salaryValue));
        Assertions.assertEquals(new BigDecimal("50000"), salary);
    }

    @Test
    void parseSalary_withNullSalary() {
        String salaryValue = null;
        Assertions.assertThrows(CsvFileParsingException.class, () -> employeeFieldsParser.parseSalary(salaryValue));
    }

    @Test
    void parseSalary_withBlankSalary() {
        String salaryValue = "   ";
        Assertions.assertThrows(CsvFileParsingException.class, () -> employeeFieldsParser.parseSalary(salaryValue));
    }

    @Test
    void parseSalary_withEmptySalary() {
        String salaryValue = "";
        Assertions.assertThrows(CsvFileParsingException.class, () -> employeeFieldsParser.parseSalary(salaryValue));
    }

    @Test
    void parseSalary_withWrongFormatSalary() {
        String salaryValue = "A45000";
        Assertions.assertThrows(CsvFileParsingException.class, () -> employeeFieldsParser.parseSalary(salaryValue));
    }

    @Test
    void parseManagerId_success() {
        String managerIdValue = "123";
        Integer managerId = Assertions.assertDoesNotThrow(() -> employeeFieldsParser.parseManagerId(managerIdValue));
        Assertions.assertEquals(123, managerId);
    }

    @Test
    void parseManagerId_withNullEmployeeId() {
        String managerIdValue = null;
        Assertions.assertThrows(CsvFileParsingException.class, () -> employeeFieldsParser.parseManagerId(managerIdValue));
    }

    @Test
    void parseManagerId_withBlankEmployeeId() {
        String managerIdValue = "   ";
        Assertions.assertThrows(CsvFileParsingException.class, () -> employeeFieldsParser.parseManagerId(managerIdValue));
    }

    @Test
    void parseManagerId_withEmptyEmployeeId() {
        String managerIdValue = "";
        Assertions.assertThrows(CsvFileParsingException.class, () -> employeeFieldsParser.parseManagerId(managerIdValue));
    }

    @Test
    void parseManagerId_withWrongFormatEmployeeId() {
        String managerIdValue = "A123";
        Assertions.assertThrows(CsvFileParsingException.class, () -> employeeFieldsParser.parseManagerId(managerIdValue));
    }
}