package org.example.employees.analyzer.services.analyzers.impl;

import org.example.employees.analyzer.domain.data.Employee;
import org.example.employees.analyzer.domain.data.EmployeeProvider;
import org.example.employees.analyzer.services.analyzers.EmployeeAnalyzer;
import org.example.employees.analyzer.services.impl.PropertiesProviderImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Optional;
import java.util.concurrent.atomic.AtomicInteger;

class ReportingLineLengthAnalyzerImplTest {
    private final EmployeeProvider employeeProvider;
    private final EmployeeAnalyzer analyzer;

    ReportingLineLengthAnalyzerImplTest() {
        this.analyzer = new ReportingLineLengthAnalyzerImpl(new PropertiesProviderImpl());
        this.employeeProvider = new EmployeeProvider();
    }


    @Test
    void analyze() {
        AtomicInteger reportingLineLength = new AtomicInteger();
        Employee employee = Assertions.assertDoesNotThrow(employeeProvider::provide);
        Optional<String> result = Assertions.assertDoesNotThrow(() -> analyzer.analyze(employee));
        Assertions.assertFalse(result.isPresent());
        employee.getSubordinates().stream()
                .filter(currentEmployee -> currentEmployee.getEmployeeId() == 124)
                .findFirst()
                .ifPresent(employee124 -> {
                    // first manager
                    reportingLineLength.getAndIncrement();
                    Assertions.assertEquals(124, employee124.getEmployeeId());
                    Optional<String> result124 = Assertions.assertDoesNotThrow(() -> analyzer.analyze(employee124));
                    Assertions.assertFalse(result124.isPresent());
                    employee124.getSubordinates().stream()
                            .filter(currentEmployee -> currentEmployee.getEmployeeId() == 300)
                            .findFirst()
                            .ifPresent(employee300 -> {
                                // second manager
                                reportingLineLength.getAndIncrement();
                                Assertions.assertEquals(300, employee300.getEmployeeId());
                                Optional<String> result300 = Assertions.assertDoesNotThrow(() -> analyzer.analyze(employee300));
                                Assertions.assertFalse(result300.isPresent());
                                employee300.getSubordinates().stream()
                                        .filter(currentEmployee -> currentEmployee.getEmployeeId() == 305)
                                        .findFirst()
                                        .ifPresent(employee305 -> {
                                            // third manager
                                            reportingLineLength.getAndIncrement();
                                            Assertions.assertEquals(305, employee305.getEmployeeId());
                                            Optional<String> result305 = Assertions.assertDoesNotThrow(() -> analyzer.analyze(employee305));
                                            Assertions.assertFalse(result305.isPresent());
                                            employee305.getSubordinates().stream()
                                                    .filter(currentEmployee -> currentEmployee.getEmployeeId() == 315)
                                                    .findFirst()
                                                    .ifPresent(employee315 -> {
                                                        // forth manager
                                                        reportingLineLength.getAndIncrement();
                                                        Assertions.assertEquals(315, employee315.getEmployeeId());
                                                        Optional<String> result315 = Assertions.assertDoesNotThrow(() -> analyzer.analyze(employee315));
                                                        Assertions.assertFalse(result315.isPresent());
                                                        employee315.getSubordinates().stream()
                                                                .filter(currentEmployee -> currentEmployee.getEmployeeId() == 316)
                                                                .findFirst()
                                                                .ifPresent(employee316 -> {
                                                                    // fifth manager
                                                                    reportingLineLength.getAndIncrement();
                                                                    Assertions.assertEquals(316, employee316.getEmployeeId());
                                                                    Optional<String> result316 = Assertions.assertDoesNotThrow(() -> analyzer.analyze(employee316));
                                                                    Assertions.assertFalse(result316.isPresent());
                                                                    employee316.getSubordinates().stream()
                                                                            .filter(currentEmployee -> currentEmployee.getEmployeeId() == 317)
                                                                            .findFirst()
                                                                            .ifPresent(employee317 -> {
                                                                                // sixth manager
                                                                                reportingLineLength.getAndIncrement();
                                                                                Assertions.assertEquals(317, employee317.getEmployeeId());
                                                                                Optional<String> result317 = Assertions.assertDoesNotThrow(() -> analyzer.analyze(employee317));
                                                                                Assertions.assertTrue(result317.isPresent());
                                                                                Assertions.assertEquals(" - reporting line is too long (by 1 levels)", result317.get());
                                                                                employee317.getSubordinates().stream()
                                                                                        .filter(currentEmployee -> currentEmployee.getEmployeeId() == 318)
                                                                                        .findFirst()
                                                                                        .ifPresent(employee318 -> {
                                                                                            Assertions.assertEquals(318, employee318.getEmployeeId());
                                                                                            Optional<String> result318 = Assertions.assertDoesNotThrow(() -> analyzer.analyze(employee318));
                                                                                            Assertions.assertTrue(result318.isPresent());
                                                                                            Assertions.assertEquals(" - reporting line is too long (by 2 levels)", result318.get());
                                                                                        });
                                                                            });
                                                                });
                                                    });
                                        });
                            });
                });
        Assertions.assertEquals(6, reportingLineLength.get());
    }
}