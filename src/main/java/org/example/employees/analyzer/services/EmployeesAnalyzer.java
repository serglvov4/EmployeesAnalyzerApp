package org.example.employees.analyzer.services;

import org.example.employees.analyzer.domain.data.StaffNode;

/**
 * Contract for class to analyze employees and report result
 */
public interface EmployeesAnalyzer {

    /**
     * Analyze employees and report result
     * @param employee Root Employee for analysis
     */
    void analyze(StaffNode employee);
}
