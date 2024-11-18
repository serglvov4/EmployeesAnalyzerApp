package org.example.employees.analyzer;

import org.example.employees.analyzer.exceptions.CsvFileParsingException;

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
            runAnalyzer(args[0]);
        }
    }

    /**
     * Run Employees Analyzer
     * @param filePath Path to the CSV file
     */
    private static void runAnalyzer(String filePath) {
        new EmployeesAnalyzerRunner(filePath).run();
    }
}