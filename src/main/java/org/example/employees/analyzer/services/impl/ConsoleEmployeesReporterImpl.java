package org.example.employees.analyzer.services.impl;

import org.example.employees.analyzer.services.EmployeesReporter;

import java.util.List;

/**
 * Print analytical report about employees to console
 */
public class ConsoleEmployeesReporterImpl implements EmployeesReporter {

    /**
     * Print report about an employee
     * @param employeeId Employee id
     * @param firstName Employee's first name
     * @param lastName Employee's last name
     * @param messages List of messages to print in report
     */
    @Override
    public void report(Integer employeeId, String firstName, String lastName, List<String> messages) {
        StringBuilder messageBuilder = new StringBuilder()
                .append("Employee ")
                .append(firstName)
                .append(" ")
                .append(lastName)
                .append(" (Id = ")
                .append(employeeId)
                .append("):")
                .append(System.lineSeparator());
        for (String message: messages) {
            messageBuilder.append("   ")
                    .append(message)
                    .append(System.lineSeparator());
        }
        System.out.println(messageBuilder);
    }

    /**
     * Print report's title
     */
    @Override
    public void printTitle() {
        System.out.println("====================================================================");
        System.out.println("                ANALYTICAL REPORT ON PERSONNEL");
        System.out.println("====================================================================");
    }

    /**
     * Print report's footer
     */
    @Override
    public void printFooter() {
        System.out.println("====================================================================");
    }
}
