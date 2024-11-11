package org.example.employees.analyzer.services;

import org.example.employees.analyzer.domain.dto.EmployeeDto;

/**
 * Contract for class to validate Employee DTO
 */
public interface EmployeeDtoValidator {

    /**
     * Validate Employee ID and Manager ID, which shouldn't be the same
     * @param employeeDto Employee DTO
     */
    void validateEmployeeIds(EmployeeDto employeeDto);
}
