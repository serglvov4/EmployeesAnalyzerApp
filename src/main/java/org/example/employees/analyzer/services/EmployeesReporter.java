package org.example.employees.analyzer.services;

import java.util.List;

/**
 * Contract for class to print analytical report about employees
 */
public interface EmployeesReporter {

    /**
     * Print report about an employee
     * @param employeeId Employee id
     * @param firstName Employee's first name
     * @param lastName Employee's last name
     * @param messages List of messages to print in report
     */
    void report(Integer employeeId, String firstName, String lastName, List<String> messages);

    /**
     * Print report's title
     */
    void printTitle();

    /**
     * Print report's footer
     */
    void printFooter();
}
