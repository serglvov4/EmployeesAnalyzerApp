package org.example.employees.analyzer.services.impl;

import org.example.employees.analyzer.domain.data.StaffNode;
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
     * @param ceoStaff Root CEO employee record
     * @param employeesDto Employees DTO collection
     */
    @Override
    public void validate(StaffNode ceoStaff, EmployeesDto employeesDto) {
        Set<Integer> employeeIds = new HashSet<>();
        collectIds(employeeIds, ceoStaff);
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
     * @param ceoStaff Parent employee record to find its subordinates
     */
    private void collectIds(Set<Integer> employeeIds, StaffNode ceoStaff) {
        employeeIds.add(ceoStaff.getEmployee().employeeId());
        ceoStaff.getSubordinates()
                .forEach(employee ->  collectIds(employeeIds, employee));
    }
}
