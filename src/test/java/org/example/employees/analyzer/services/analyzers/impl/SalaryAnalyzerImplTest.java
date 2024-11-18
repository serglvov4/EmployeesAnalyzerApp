package org.example.employees.analyzer.services.analyzers.impl;

import org.example.employees.analyzer.domain.data.StaffNode;
import org.example.employees.analyzer.domain.data.StaffTreeProvider;
import org.example.employees.analyzer.services.analyzers.EmployeeAnalyzer;
import org.example.employees.analyzer.services.impl.PropertiesProviderImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Optional;

class SalaryAnalyzerImplTest {
    private final StaffTreeProvider staffTreeProvider;
    private final EmployeeAnalyzer analyzer;

    SalaryAnalyzerImplTest() {
        this.analyzer = new SalaryAnalyzerImpl(new PropertiesProviderImpl());
        this.staffTreeProvider = new StaffTreeProvider();
    }

    @Test
    void analyze() {
        StaffNode staff = Assertions.assertDoesNotThrow(staffTreeProvider::provide);
        Optional<String> result = Assertions.assertDoesNotThrow(() -> analyzer.analyze(staff));
        Assertions.assertFalse(result.isPresent());
        staff.getSubordinates().stream()
                .filter(currentEmployee -> currentEmployee.getEmployee().employeeId() == 124)
                .findFirst()
                .ifPresent(employee124 -> {
                    Assertions.assertEquals(124, employee124.getEmployee().employeeId());
                    Optional<String> result124 = Assertions.assertDoesNotThrow(() -> analyzer.analyze(employee124));
                    Assertions.assertTrue(result124.isPresent());
                    Assertions.assertEquals(" - earns less than it should be (by 285004.67)", result124.get());
                    employee124.getSubordinates().stream()
                            .filter(currentEmployee -> currentEmployee.getEmployee().employeeId() == 300)
                            .findFirst()
                            .ifPresent(employee300 -> {
                                Assertions.assertEquals(300, employee300.getEmployee().employeeId());
                                Optional<String> result300 = Assertions.assertDoesNotThrow(() -> analyzer.analyze(employee300));
                                Assertions.assertFalse(result300.isPresent());
                                employee300.getSubordinates().stream()
                                        .filter(currentEmployee -> currentEmployee.getEmployee().employeeId() == 305)
                                        .findFirst()
                                        .ifPresent(employee305 -> {
                                                Assertions.assertEquals(305, employee305.getEmployee().employeeId());
                                            Optional<String> result305 = Assertions.assertDoesNotThrow(() -> analyzer.analyze(employee305));
                                            Assertions.assertTrue(result305.isPresent());
                                            Assertions.assertEquals(" - earns more than it should be (by 28000.00)", result305.get());
                                        });
                            });
                });
    }
}