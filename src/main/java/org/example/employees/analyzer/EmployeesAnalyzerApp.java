package org.example.employees.analyzer;

import org.example.employees.analyzer.domain.data.Employee;
import org.example.employees.analyzer.domain.dto.EmployeesDto;
import org.example.employees.analyzer.exceptions.CsvFileParsingException;
import org.example.employees.analyzer.services.*;
import org.example.employees.analyzer.services.analyzers.EmployeeAnalyzer;
import org.example.employees.analyzer.services.analyzers.impl.ReportingLineLengthAnalyzerImpl;
import org.example.employees.analyzer.services.analyzers.impl.SalaryAnalyzerImpl;
import org.example.employees.analyzer.services.impl.*;

import java.util.List;

/**
 * Application for preparing analytical report about employees
 */
public class EmployeesAnalyzerApp {

    /**
     * Application entry point main function
     * @param args The first parameter should be the path to CSV data file
     */
    public static void main(String[] args) {
        if(args.length == 0) {
            throw new CsvFileParsingException("Path to the CSV file should be provided.");
        } else {
            analyzeEmployees(args[0]);
        }
    }

    /**
     * Parse CSV file, validate, build report data and print analytical report about employees
     * @param filePath The path to the CSV file with employees data
     */
    private static void analyzeEmployees(String filePath) {
        EmployeeDtoValidator employeeDtoValidator = new EmployeeDtoValidatorImpl();
        EmployeeFieldsParser employeeFieldsParser = new EmployeeFieldsParserImpl();
        CsvFileParser fileParser = new CsvFileParserImpl(employeeDtoValidator, employeeFieldsParser);
        EmployeesDto employeesDto = fileParser.parse(filePath);
        Employee ceoEmployeeTree = new EmployeesTreeBuilderImpl().build(employeesDto);
        HierarchyValidator hierarchyValidator = new HierarchyValidatorImpl();
        hierarchyValidator.validate(ceoEmployeeTree, employeesDto);
        EmployeesReporter employeesReporter = new ConsoleEmployeesReporterImpl();
        PropertiesProvider propertiesProvider = new PropertiesProviderImpl();
        List<EmployeeAnalyzer> analyzers = List.of(
                new SalaryAnalyzerImpl(propertiesProvider),
                new ReportingLineLengthAnalyzerImpl(propertiesProvider)
        );
        EmployeesAnalyzer employeesAnalyzer = new EmployeesAnalyzerImpl(employeesReporter, analyzers);
        employeesAnalyzer.analyze(ceoEmployeeTree);
    }
}