package org.example.employees.analyzer.services.analyzers.impl;

import org.example.employees.analyzer.domain.data.StaffNode;
import org.example.employees.analyzer.domain.data.StaffTreeProvider;
import org.example.employees.analyzer.services.analyzers.EmployeeAnalyzer;
import org.example.employees.analyzer.services.impl.PropertiesProviderImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Optional;
import java.util.concurrent.atomic.AtomicInteger;

class ReportingLineLengthAnalyzerImplTest {
    private final StaffTreeProvider staffTreeProvider;
    private final EmployeeAnalyzer analyzer;

    ReportingLineLengthAnalyzerImplTest() {
        this.analyzer = new ReportingLineLengthAnalyzerImpl(new PropertiesProviderImpl());
        this.staffTreeProvider = new StaffTreeProvider();
    }

    @Test
    void analyze() {
        AtomicInteger reportingLineLength = new AtomicInteger();
        StaffNode staff = staffTreeProvider.provide();
        Optional<String> result = Assertions.assertDoesNotThrow(() -> analyzer.analyze(staff));
        Assertions.assertFalse(result.isPresent());
        staff.getSubordinates().stream()
                .filter(currentEmployee -> currentEmployee.getEmployee().employeeId() == 124)
                .findFirst()
                .ifPresent(employee124 -> {
                    // first manager
                    reportingLineLength.getAndIncrement();
                    Assertions.assertEquals(124, employee124.getEmployee().employeeId());
                    Optional<String> result124 = analyzer.analyze(employee124);
                    Assertions.assertFalse(result124.isPresent());
                    employee124.getSubordinates().stream()
                            .filter(currentEmployee -> currentEmployee.getEmployee().employeeId() == 300)
                            .findFirst()
                            .ifPresent(employee300 -> {
                                // second manager
                                reportingLineLength.getAndIncrement();
                                Assertions.assertEquals(300, employee300.getEmployee().employeeId());
                                Optional<String> result300 = analyzer.analyze(employee300);
                                Assertions.assertFalse(result300.isPresent());
                                employee300.getSubordinates().stream()
                                        .filter(currentEmployee -> currentEmployee.getEmployee().employeeId() == 305)
                                        .findFirst()
                                        .ifPresent(employee305 -> {
                                            // third manager
                                            reportingLineLength.getAndIncrement();
                                            Assertions.assertEquals(305, employee305.getEmployee().employeeId());
                                            Optional<String> result305 = analyzer.analyze(employee305);
                                            Assertions.assertFalse(result305.isPresent());
                                            employee305.getSubordinates().stream()
                                                    .filter(currentEmployee -> currentEmployee.getEmployee().employeeId() == 315)
                                                    .findFirst()
                                                    .ifPresent(employee315 -> {
                                                        // forth manager
                                                        reportingLineLength.getAndIncrement();
                                                        Assertions.assertEquals(315, employee315.getEmployee().employeeId());
                                                        Optional<String> result315 = analyzer.analyze(employee315);
                                                        Assertions.assertFalse(result315.isPresent());
                                                        employee315.getSubordinates().stream()
                                                                .filter(currentEmployee -> currentEmployee.getEmployee().employeeId() == 316)
                                                                .findFirst()
                                                                .ifPresent(employee316 -> {
                                                                    // fifth manager
                                                                    reportingLineLength.getAndIncrement();
                                                                    Assertions.assertEquals(316, employee316.getEmployee().employeeId());
                                                                    Optional<String> result316 = analyzer.analyze(employee316);
                                                                    Assertions.assertFalse(result316.isPresent());
                                                                    employee316.getSubordinates().stream()
                                                                            .filter(currentEmployee -> currentEmployee.getEmployee().employeeId() == 317)
                                                                            .findFirst()
                                                                            .ifPresent(employee317 -> {
                                                                                // sixth manager
                                                                                reportingLineLength.getAndIncrement();
                                                                                Assertions.assertEquals(317, employee317.getEmployee().employeeId());
                                                                                Optional<String> result317 = analyzer.analyze(employee317);
                                                                                Assertions.assertTrue(result317.isPresent());
                                                                                Assertions.assertEquals(" - reporting line is too long (by 1 levels)", result317.get());
                                                                                employee317.getSubordinates().stream()
                                                                                        .filter(currentEmployee -> currentEmployee.getEmployee().employeeId() == 318)
                                                                                        .findFirst()
                                                                                        .ifPresent(employee318 -> {
                                                                                            Assertions.assertEquals(318, employee318.getEmployee().employeeId());
                                                                                            Optional<String> result318 = analyzer.analyze(employee318);
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