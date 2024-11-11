package org.example.employees.analyzer.domain.dto;

import org.example.employees.analyzer.services.CsvFileParser;
import org.example.employees.analyzer.services.EmployeeDtoValidator;
import org.example.employees.analyzer.services.EmployeeFieldsParser;
import org.example.employees.analyzer.services.impl.CsvFileParserImpl;
import org.example.employees.analyzer.services.impl.EmployeeDtoValidatorImpl;
import org.example.employees.analyzer.services.impl.EmployeeFieldsParserImpl;

import java.net.URISyntaxException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Objects;

public class EmployeesDtoProvider {

    /**
     * Provide correct Employees DTO collection
     * @return Employees DTO collection
     */
    public EmployeesDto provide() {
        return getEmployeesDtoFromFile("employees.csv");
    }

    /**
     * Provide Employees DTO collection without CEO record
     * @return Employees DTO collection without CEO record
     */
    public EmployeesDto provideWithoutCeoRecord() {
        return getEmployeesDtoFromFile("employees_withoutCeoRecord.csv");
    }

    /**
     * Provide Employees DTO collection with wrong hierarchy
     * @return Employees DTO collection with wrong hierarchy
     */
    public EmployeesDto provideWithWrongHierarchy() {
        return getEmployeesDtoFromFile("employees_withWrongHierarchy.csv");
    }

    /**
     * Get Employees DTO collection from CSV file
     * @param fileName Name of file from test resources folder
     * @return Employees DTO collection
     */
    private EmployeesDto getEmployeesDtoFromFile(String fileName) {
        Path filePath;
        try {
            filePath = Paths.get(Objects.requireNonNull(getClass().getClassLoader().getResource(fileName)).toURI());
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }
        EmployeeDtoValidator employeeDtoValidator = new EmployeeDtoValidatorImpl();
        EmployeeFieldsParser employeeFieldsParser = new EmployeeFieldsParserImpl();
        CsvFileParser fileParser = new CsvFileParserImpl(employeeDtoValidator, employeeFieldsParser);
        return fileParser.parse(filePath.toString());
    }
}


