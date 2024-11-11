package org.example.employees.analyzer.services.analyzers.impl;

import org.example.employees.analyzer.domain.data.Employee;
import org.example.employees.analyzer.services.PropertiesProvider;
import org.example.employees.analyzer.services.analyzers.EmployeeAnalyzer;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Optional;

/**
 * Employee's salary analyzer
 */
public class SalaryAnalyzerImpl implements EmployeeAnalyzer {
    private final PropertiesProvider propertiesProvider;

    public SalaryAnalyzerImpl(PropertiesProvider propertiesProvider) {
        this.propertiesProvider = propertiesProvider;
    }

    /**
     * Analyze employee's salary
     * @param employee Employee
     * @return Result of salary analysis
     */
    @Override
    public Optional<String> analyze(Employee employee) {
        String message = null;
        if(!employee.getSubordinates().isEmpty()) {
            BigDecimal averageSubordinatesSalary = employee.getAverageSubordinatesSalary();
            BigDecimal lackOfSalary = employee.getSalary()
                    .subtract(averageSubordinatesSalary.multiply(propertiesProvider.getMinimalSalaryRateLimit()))
                    .negate().setScale(2, RoundingMode.HALF_UP);
            if (lackOfSalary.compareTo(BigDecimal.ZERO) > 0) {
                message = " - earns less than it should be (by " + lackOfSalary + ")";
            } else {
                BigDecimal excessSalary = employee.getSalary()
                        .subtract(averageSubordinatesSalary.multiply(propertiesProvider.getMaximalSalaryRateLimit()))
                        .setScale(2, RoundingMode.HALF_UP);
                if (excessSalary.compareTo(BigDecimal.ZERO) > 0) {
                    message = " - earns more than it should be (by " + excessSalary + ")";
                }
            }
        }
        return Optional.ofNullable(message);
    }
}
