package org.example.employees.analyzer.services.analyzers.impl;

import org.example.employees.analyzer.domain.data.StaffNode;
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
     * @param staff Staff
     * @return Result of salary analysis
     */
    @Override
    public Optional<String> analyze(StaffNode staff) {
        String message = null;
        if(!staff.getSubordinates().isEmpty()) {
            BigDecimal averageSubordinatesSalary = staff.getAverageSubordinatesSalary();
            BigDecimal lackOfSalary = staff.getEmployee().salary()
                    .subtract(averageSubordinatesSalary.multiply(propertiesProvider.getMinimalSalaryRateLimit()))
                    .negate().setScale(2, RoundingMode.HALF_UP);
            if (lackOfSalary.compareTo(BigDecimal.ZERO) > 0) {
                message = " - earns less than it should be (by " + lackOfSalary + ")";
            } else {
                BigDecimal excessSalary = staff.getEmployee().salary()
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
