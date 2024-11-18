package org.example.employees.analyzer.services.impl;

import org.example.employees.analyzer.domain.data.StaffNode;
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
        StaffNode staff = Assertions.assertDoesNotThrow(() -> employeesTreeBuilder.build(employeesDto));
        Assertions.assertNotNull(staff);
        Assertions.assertDoesNotThrow(() -> hierarchyValidator.validate(staff, employeesDto));
        Assertions.assertEquals(123, staff.getEmployee().employeeId());
        Assertions.assertEquals("Joe", staff.getEmployee().firstName());
        Assertions.assertEquals("Doe", staff.getEmployee().lastName());
        Assertions.assertEquals(new BigDecimal("60000"), staff.getEmployee().salary());
        Assertions.assertNull(staff.getManager());
        Assertions.assertEquals(0, staff.getHierarchyLevel());
        Assertions.assertEquals(2, staff.getSubordinates().size());
        Assertions.assertEquals(new BigDecimal("46000.00"), staff.getAverageSubordinatesSalary());
        staff.getSubordinates().stream()
                .max(Comparator.comparing(o -> o.getSubordinates().size()))
                .ifPresent(emp -> {
                    Assertions.assertEquals(2, emp.getSubordinates().size());
                    Assertions.assertEquals(124, emp.getEmployee().employeeId());
                    Assertions.assertEquals("Martin", emp.getEmployee().firstName());
                    Assertions.assertEquals("Chekov", emp.getEmployee().lastName());
                    Assertions.assertEquals(new BigDecimal("45000"), emp.getEmployee().salary());
                    Assertions.assertEquals(123, emp.getManager().getEmployee().employeeId());
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