package org.example.employees.analyzer.services.analyzers.impl;

import org.example.employees.analyzer.domain.data.Employee;
import org.example.employees.analyzer.domain.data.EmployeeProvider;
import org.example.employees.analyzer.services.analyzers.EmployeeAnalyzer;
import org.example.employees.analyzer.services.impl.PropertiesProviderImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Optional;

class SalaryAnalyzerImplTest {
    private final EmployeeProvider employeeProvider;
    private final EmployeeAnalyzer analyzer;

    SalaryAnalyzerImplTest() {
        this.analyzer = new SalaryAnalyzerImpl(new PropertiesProviderImpl());
        this.employeeProvider = new EmployeeProvider();
    }

    @Test
    void analyze() {
        Employee employee = Assertions.assertDoesNotThrow(employeeProvider::provide);
        Optional<String> result = Assertions.assertDoesNotThrow(() -> analyzer.analyze(employee));
        Assertions.assertFalse(result.isPresent());
        employee.getSubordinates().stream()
                .filter(currentEmployee -> currentEmployee.getEmployeeId() == 124)
                .findFirst()
                .ifPresent(employee124 -> {
                    Assertions.assertEquals(124, employee124.getEmployeeId());
                    Optional<String> result124 = Assertions.assertDoesNotThrow(() -> analyzer.analyze(employee124));
                    Assertions.assertTrue(result124.isPresent());
                    Assertions.assertEquals(" - earns less than it should be (by 285004.67)", result124.get());
                    employee124.getSubordinates().stream()
                            .filter(currentEmployee -> currentEmployee.getEmployeeId() == 300)
                            .findFirst()
                            .ifPresent(employee300 -> {
                                Assertions.assertEquals(300, employee300.getEmployeeId());
                                Optional<String> result300 = Assertions.assertDoesNotThrow(() -> analyzer.analyze(employee300));
                                Assertions.assertFalse(result300.isPresent());
                                employee300.getSubordinates().stream()
                                        .filter(currentEmployee -> currentEmployee.getEmployeeId() == 305)
                                        .findFirst()
                                        .ifPresent(employee305 -> {
                                                Assertions.assertEquals(305, employee305.getEmployeeId());
                                            Optional<String> result305 = Assertions.assertDoesNotThrow(() -> analyzer.analyze(employee305));
                                            Assertions.assertTrue(result305.isPresent());
                                            Assertions.assertEquals(" - earns more than it should be (by 28000.00)", result305.get());
                                        });
                            });
                });
    }
}