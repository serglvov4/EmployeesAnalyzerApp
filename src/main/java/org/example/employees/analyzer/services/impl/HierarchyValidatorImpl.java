package org.example.employees.analyzer.services.impl;

import org.example.employees.analyzer.domain.data.Employee;
import org.example.employees.analyzer.domain.dto.EmployeesDto;
import org.example.employees.analyzer.exceptions.LogicalIntegrityException;
import org.example.employees.analyzer.services.HierarchyValidator;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Validate that all employees be subordinate (direct or indirect) the CEO
 */
public class HierarchyValidatorImpl implements HierarchyValidator {

    /**
     * Checks that all employees be subordinate (direct or indirect) the CEO
     * @param ceoEmployee Root CEO employee record
     * @param employeesDto Employees DTO collection
     */
    @Override
    public void validate(Employee ceoEmployee, EmployeesDto employeesDto) {
        Set<Integer> employeeIds = new HashSet<>();
        collectIds(employeeIds, ceoEmployee);
        List<Integer> wrongIds = employeesDto.getEmployeesIds().stream()
                .filter(employeeId ->  !employeeIds.contains(employeeId))
                .sorted()
                .toList();
        if(!wrongIds.isEmpty()) {
            throw new LogicalIntegrityException("Invalid CSV file: wrong employees' hierarchy. Employee IDs = " + wrongIds);
        }
    }

    /**
     * Collects identifiers of all direct and indirect subordinates the CEO
     * @param employeeIds Employee IDs collection
     * @param ceoEmployee Parent employee record to find its subordinates
     */
    private void collectIds(Set<Integer> employeeIds, Employee ceoEmployee) {
        employeeIds.add(ceoEmployee.getEmployeeId());
        ceoEmployee.getSubordinates()
                .forEach(employee ->  collectIds(employeeIds, employee));
    }
}
