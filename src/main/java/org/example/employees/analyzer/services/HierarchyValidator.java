package org.example.employees.analyzer.services;

import org.example.employees.analyzer.domain.data.Employee;
import org.example.employees.analyzer.domain.dto.EmployeesDto;

/**
 * Contract for class to validate that all employees be subordinate (direct or indirect) the CEO
 */
public interface HierarchyValidator {

    /**
     * Checks that all employees be subordinate (direct or indirect) the CEO
     * @param ceoEmployee Root CEO employee record
     * @param employeesDto Employees DTO collection
     */
    void validate(Employee ceoEmployee, EmployeesDto employeesDto);
}
