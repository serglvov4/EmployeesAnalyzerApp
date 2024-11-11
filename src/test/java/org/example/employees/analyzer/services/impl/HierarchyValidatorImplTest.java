package org.example.employees.analyzer.services.impl;

import org.example.employees.analyzer.domain.data.Employee;
import org.example.employees.analyzer.domain.dto.EmployeesDto;
import org.example.employees.analyzer.domain.dto.EmployeesDtoProvider;
import org.example.employees.analyzer.exceptions.LogicalIntegrityException;
import org.example.employees.analyzer.services.EmployeesTreeBuilder;
import org.example.employees.analyzer.services.HierarchyValidator;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class HierarchyValidatorImplTest {
    private final HierarchyValidator hierarchyValidator;
    private final EmployeesTreeBuilder employeesTreeBuilder;
    private final EmployeesDtoProvider employeesDtoProvider;

    public HierarchyValidatorImplTest() {
        this.hierarchyValidator = new HierarchyValidatorImpl();
        this.employeesTreeBuilder = new EmployeesTreeBuilderImpl();
        this.employeesDtoProvider = new EmployeesDtoProvider();
    }

    @Test
    void validate_success() {
        EmployeesDto employeesDto = employeesDtoProvider.provide();
        Employee employeeCeo = employeesTreeBuilder.build(employeesDto);
        Assertions.assertDoesNotThrow(() -> hierarchyValidator.validate(employeeCeo, employeesDto));
    }

    @Test
    void validate_withWrongHierarchy() {
        EmployeesDto employeesDto = employeesDtoProvider.provideWithWrongHierarchy();
        Employee employeeCeo = employeesTreeBuilder.build(employeesDto);
        LogicalIntegrityException exception = Assertions.assertThrows(LogicalIntegrityException.class,
                () -> hierarchyValidator.validate(employeeCeo, employeesDto));
        Assertions.assertEquals(
                "Invalid CSV file: wrong employees' hierarchy. Employee IDs = [125, 315, 316, 317, 318]",
                exception.getMessage());
    }
}