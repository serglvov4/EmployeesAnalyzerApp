package org.example.employees.analyzer.services.impl;

import org.example.employees.analyzer.domain.dto.EmployeeDto;
import org.example.employees.analyzer.exceptions.LogicalIntegrityException;
import org.example.employees.analyzer.services.EmployeeDtoValidator;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

class EmployeeDtoValidatorImplTest {
    private final EmployeeDtoValidator employeeDtoValidator = new EmployeeDtoValidatorImpl();

    @Test
    void validateEmployeeIds_success() {
        EmployeeDto employeeDto = new EmployeeDto(
                124,
                "Martin",
                "Chekov",
                new BigDecimal("45000"),
                123);
        Assertions.assertDoesNotThrow(() -> employeeDtoValidator.validateEmployeeIds(employeeDto));
    }

    @Test
    void validateEmployeeIds_withTheSameIdAndManagerId() {
        EmployeeDto employeeDto = new EmployeeDto(
                124,
                "Martin",
                "Chekov",
                new BigDecimal("45000"),
                124);
        LogicalIntegrityException exception = Assertions.assertThrows(LogicalIntegrityException.class, () -> employeeDtoValidator.validateEmployeeIds(employeeDto));
        Assertions.assertNotNull(exception);
        Assertions.assertEquals("Manager Id shouldn't have the same value as Employee Id.", exception.getMessage());
    }
}