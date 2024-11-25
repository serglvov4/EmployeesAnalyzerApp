package org.example.employees.analyzer.services.analyzers;

import org.example.employees.analyzer.domain.data.StaffNode;

import java.util.Optional;

/**
 * Contract for class to analyze employee
 */
public interface EmployeeAnalyzer {

    /**
     * Analyze employee
     * @param employee Employee
     * @return Result of analysis
     */
    Optional<String> analyze(StaffNode employee);
}
