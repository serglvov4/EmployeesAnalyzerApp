package org.example.employees.analyzer.services.impl;

import org.example.employees.analyzer.domain.dto.EmployeeDto;
import org.example.employees.analyzer.exceptions.LogicalIntegrityException;
import org.example.employees.analyzer.services.EmployeeDtoValidator;

import java.util.Objects;

/**
 * Validate Employee DTO
 */
public class EmployeeDtoValidatorImpl implements EmployeeDtoValidator {

    /**
     * Validate Employee ID and Manager ID, which shouldn't be the same
     * @param employeeDto Employee DTO
     */
    @Override
    public void validateEmployeeIds(EmployeeDto employeeDto) {
        if(Objects.equals(employeeDto.id(), employeeDto.managerId())) {
            throw new LogicalIntegrityException("Manager Id shouldn't have the same value as Employee Id.");
        }
    }
}
