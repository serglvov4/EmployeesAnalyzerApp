package org.example.employees.analyzer.services.analyzers.impl;

import org.example.employees.analyzer.domain.data.StaffNode;
import org.example.employees.analyzer.services.PropertiesProvider;
import org.example.employees.analyzer.services.analyzers.EmployeeAnalyzer;

import java.util.Optional;

/**
 *  Employee's reporting line length analyzer
 */
public class ReportingLineLengthAnalyzerImpl implements EmployeeAnalyzer {
    private final PropertiesProvider propertiesProvider;

    public ReportingLineLengthAnalyzerImpl(PropertiesProvider propertiesProvider) {
        this.propertiesProvider = propertiesProvider;
    }

    /**
     * Analyze employee's reporting line length
     * @param employee Employee
     * @return Result of reporting line length analysis
     */
    @Override
    public Optional<String> analyze(StaffNode employee) {
        String message = null;
        if (employee.getHierarchyLevel() > (propertiesProvider.getMaximalReportingLineLength() + 1)) {
            message = " - reporting line is too long (by " + (employee.getHierarchyLevel() - propertiesProvider.getMaximalReportingLineLength() - 1) + " levels)";
        }
        return Optional.ofNullable(message);
    }
}
