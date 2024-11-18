package org.example.employees.analyzer.services.impl;

import org.example.employees.analyzer.domain.dto.EmployeeDto;
import org.example.employees.analyzer.domain.dto.EmployeesDto;
import org.example.employees.analyzer.exceptions.CsvFileParsingException;
import org.example.employees.analyzer.exceptions.LogicalIntegrityException;
import org.example.employees.analyzer.services.CsvFileParser;
import org.example.employees.analyzer.services.EmployeeDtoValidator;
import org.example.employees.analyzer.services.EmployeeFieldsParser;

import java.io.BufferedReader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;

/**
 * Parse CSV file and create Employees DTO collection
 */
public class CsvFileParserImpl implements CsvFileParser {
    private static final String DELIMITER = ",";
    private final EmployeeFieldsParser fieldsParser;
    private final EmployeeDtoValidator employeeDtoValidator;

    public CsvFileParserImpl(EmployeeDtoValidator employeeDtoValidator, EmployeeFieldsParser fieldsParser) {
        this.employeeDtoValidator = employeeDtoValidator;
        this.fieldsParser = fieldsParser;
    }

    /**
     * Parse CSV file with information about employees
     * @param filePath System path to the CSV file
     * @return a collection of Employee's DTO
     */
    @Override
    public EmployeesDto parse(String filePath) {
        EmployeesDto employeesDto = new EmployeesDto();
        try (BufferedReader reader = Files.newBufferedReader(Paths.get(filePath))) {
            reader.lines()
                    .skip(1)
                    .map(this::getFieldValues)
                    .map(this::createEmployeeDto)
                    .forEach(employeeDto -> {
                        employeeDtoValidator.validateEmployeeIds(employeeDto);
                        employeesDto.addEmployeeDto(employeeDto);
                    });
        } catch (LogicalIntegrityException logicalIntegrityException) {
            throw logicalIntegrityException;
        } catch (Exception exception) {
            throw new CsvFileParsingException("CSV file parsing error: " + exception.getMessage());
        }
        return employeesDto;
    }

    /**
     * Split line from file and get values of fields
     * @param line Line from file
     * @return Values of fields for Employee DTO
     */
    private List<String> getFieldValues(String line) {
        if (line.isBlank()) {
            throw new CsvFileParsingException("Line from file is empty");
        }
        List<String> employeeFields = Arrays.asList(line.trim().split(DELIMITER));
        if(employeeFields.size() == 1) {
            throw new CsvFileParsingException("Fields' values could not be separated: " + employeeFields);
        } else if (employeeFields.size() < 4) {
            throw new LogicalIntegrityException("Not all field values are presented in the file: " + employeeFields);
        }
        return employeeFields;
    }


    /**
     * Create Employee DTO using information from the CSV file
     * @param employeeFields Values of fields from a row in CSV file
     * @return Employee DTO record
     */
    private EmployeeDto createEmployeeDto(List<String> employeeFields) {
        Integer managerId = employeeFields.size() > 4 ? fieldsParser.parseManagerId(employeeFields.get(4)) : null;
        return new EmployeeDto(fieldsParser.parseEmployeeId(employeeFields.getFirst()),
                fieldsParser.parseFirstName(employeeFields.get(1)),
                fieldsParser.parseLastName(employeeFields.get(2)),
                fieldsParser.parseSalary(employeeFields.get(3)),
                managerId);
    }
}
