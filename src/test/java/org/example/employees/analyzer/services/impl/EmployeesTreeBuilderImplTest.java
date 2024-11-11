package org.example.employees.analyzer.services.impl;

import org.example.employees.analyzer.domain.data.Employee;
import org.example.employees.analyzer.domain.dto.EmployeesDto;
import org.example.employees.analyzer.domain.dto.EmployeesDtoProvider;
import org.example.employees.analyzer.exceptions.LogicalIntegrityException;
import org.example.employees.analyzer.services.EmployeesTreeBuilder;
import org.example.employees.analyzer.services.HierarchyValidator;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.Comparator;

class EmployeesTreeBuilderImplTest {
    private final EmployeesTreeBuilder employeesTreeBuilder;
    private final EmployeesDtoProvider employeesDtoProvider;
    private final HierarchyValidator hierarchyValidator;

    EmployeesTreeBuilderImplTest() {
        this.hierarchyValidator = new HierarchyValidatorImpl();
        this.employeesTreeBuilder = new EmployeesTreeBuilderImpl();
        this.employeesDtoProvider = new EmployeesDtoProvider();
    }

    @Test
    void build_success() {
        EmployeesDto employeesDto = employeesDtoProvider.provide();
        Employee employee = Assertions.assertDoesNotThrow(() -> employeesTreeBuilder.build(employeesDto));
        Assertions.assertNotNull(employee);
        Assertions.assertDoesNotThrow(() -> hierarchyValidator.validate(employee, employeesDto));
        Assertions.assertEquals(123, employee.getEmployeeId());
        Assertions.assertEquals("Joe", employee.getFirstName());
        Assertions.assertEquals("Doe", employee.getLastName());
        Assertions.assertEquals(new BigDecimal("60000"), employee.getSalary());
        Assertions.assertNull(employee.getManager());
        Assertions.assertEquals(0, employee.getHierarchyLevel());
        Assertions.assertEquals(2, employee.getSubordinates().size());
        Assertions.assertEquals(new BigDecimal("46000.00"), employee.getAverageSubordinatesSalary());
        employee.getSubordinates().stream()
                .max(Comparator.comparing(o -> o.getSubordinates().size()))
                .ifPresent(emp -> {
                    Assertions.assertEquals(2, emp.getSubordinates().size());
                    Assertions.assertEquals(124, emp.getEmployeeId());
                    Assertions.assertEquals("Martin", emp.getFirstName());
                    Assertions.assertEquals("Chekov", emp.getLastName());
                    Assertions.assertEquals(new BigDecimal("45000"), emp.getSalary());
                    Assertions.assertEquals(123, emp.getManager().getEmployeeId());
                    emp.getSubordinates()
                            .forEach(empl -> Assertions.assertEquals(2, empl.getHierarchyLevel()));
                });
    }

    @Test
    void build_withoutCeoRecord() {
        EmployeesDto employeesDto = employeesDtoProvider.provideWithoutCeoRecord();
        LogicalIntegrityException exception = Assertions.assertThrows(LogicalIntegrityException.class,
                () -> employeesTreeBuilder.build(employeesDto));
        Assertions.assertEquals("Record for CEO should be present in file.", exception.getMessage());
    }

}