package org.example.employees.analyzer.services.impl;

import org.example.employees.analyzer.domain.data.StaffNode;
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
     * @param staff Staff for analysis
     */
    public void analyze(StaffNode staff) {
        employeesReporter.printTitle();
        if (Objects.nonNull(staff)) {
            analyzeEmployeesTree(staff);
        }
        employeesReporter.printFooter();
    }

    /**
     * Analyze employees tree
     * @param staff Root Employee for analysis
     */
    private void analyzeEmployeesTree(StaffNode staff) {
        analyzeEmployee(staff);
        staff.getSubordinates().stream()
                .sorted(Comparator.comparingInt(subStaff -> subStaff.getEmployee().employeeId()))
                .forEach(this::analyzeEmployeesTree);
    }

    /**
     * Perform analysis using list of employee analyzers and report result
     * @param staff Analyzing Staff
     */
    private void analyzeEmployee(StaffNode staff) {
        List<String> messages = new ArrayList<>();
        analyzers.forEach(employeeAnalyzer ->
                employeeAnalyzer.analyze(staff).ifPresent(messages::add));
        if(!messages.isEmpty()) {
            employeesReporter.report(staff.getEmployee().employeeId(),
                    staff.getEmployee().firstName(),
                    staff.getEmployee().lastName(),
                    messages);
        }
    }
}
