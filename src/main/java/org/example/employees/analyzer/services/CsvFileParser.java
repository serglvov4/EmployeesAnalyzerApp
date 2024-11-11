package org.example.employees.analyzer.services;

import org.example.employees.analyzer.domain.dto.EmployeesDto;

/**
 * Contract for class to parse CSV file and create Employees DTO collection
 */
public interface CsvFileParser {

    /**
     * Parse CSV file with information about employees
     * @param filePath System path to the CSV file
     * @return a collection of Employee's DTO
     */
    EmployeesDto parse(String filePath);
}
