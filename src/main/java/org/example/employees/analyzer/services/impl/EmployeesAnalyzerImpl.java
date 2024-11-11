package org.example.employees.analyzer.services.impl;

import org.example.employees.analyzer.domain.data.Employee;
import org.example.employees.analyzer.services.EmployeesAnalyzer;
import org.example.employees.analyzer.services.EmployeesReporter;
import org.example.employees.analyzer.services.analyzers.EmployeeAnalyzer;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;

/**
 * Analyze employees and report result
 */
public class EmployeesAnalyzerImpl implements EmployeesAnalyzer {
    private final EmployeesReporter employeesReporter;
    private final List<EmployeeAnalyzer> analyzers;

    public EmployeesAnalyzerImpl(EmployeesReporter employeesReporter,
                                 List<EmployeeAnalyzer> analyzers) {
        this.employeesReporter = employeesReporter;
        this.analyzers = analyzers;
    }

    /**
     * Analyze employees and report result
     * @param employee Root Employee for analysis
     */
    public void analyze(Employee employee) {
        employeesReporter.printTitle();
        if (Objects.nonNull(employee)) {
            analyzeEmployeesTree(employee);
        }
        employeesReporter.printFooter();
    }

    /**
     * Analyze employees tree
     * @param employee Root Employee for analysis
     */
    private void analyzeEmployeesTree(Employee employee) {
        analyzeEmployee(employee);
        employee.getSubordinates().stream()
                .sorted(Comparator.comparingInt(Employee::getEmployeeId))
                .forEach(this::analyzeEmployeesTree);
    }

    /**
     * Perform analysis using list of employee analyzers and report result
     * @param employee Analyzing Employee
     */
    private void analyzeEmployee(Employee employee) {
        List<String> messages = new ArrayList<>();
        analyzers.forEach(employeeAnalyzer ->
                employeeAnalyzer.analyze(employee).ifPresent(messages::add));
        if(!messages.isEmpty()) {
            employeesReporter.report(employee.getEmployeeId(),
                    employee.getFirstName(),
                    employee.getLastName(),
                    messages);
        }
    }
}
