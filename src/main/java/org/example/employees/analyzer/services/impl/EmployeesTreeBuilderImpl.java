package org.example.employees.analyzer.services.impl;

import org.example.employees.analyzer.domain.data.Employee;
import org.example.employees.analyzer.domain.data.StaffNode;
import org.example.employees.analyzer.domain.dto.EmployeeDto;
import org.example.employees.analyzer.domain.dto.EmployeesDto;
import org.example.employees.analyzer.exceptions.LogicalIntegrityException;
import org.example.employees.analyzer.services.EmployeesTreeBuilder;

import java.util.Map;
import java.util.Objects;
import java.util.Set;

/**
 * Build employee tree - hierarchical data structure
 */
public class EmployeesTreeBuilderImpl implements EmployeesTreeBuilder {

    /**
     * Build employee tree (hierarchical data structure) using Employees DTO collection
     * @param employeesDto Employees DTO collection
     * @return Employee tree hierarchical structure
     */
    @Override
    public StaffNode build(EmployeesDto employeesDto) {
        EmployeeDto ceoDto = employeesDto.getCEO()
                .orElseThrow(() -> new LogicalIntegrityException("Record for CEO should be present in file."));
        StaffNode ceo = new StaffNode(
                new Employee(ceoDto.id(),
                        ceoDto.firstName(),
                        ceoDto.lastName(),
                        ceoDto.salary()),
                null);
        Map<Integer, Set<EmployeeDto>> subordinates = employeesDto.getSubordinates();
        fillSubordinatesTree(subordinates, ceo);
        return ceo;
    }

    /**
     * Fill subordinates tree using Employees DTO collection grouped by manager id
     * @param subordinates Employees DTO collection grouped by manager id
     * @param staff Employee tree hierarchical structure
     */
    private void fillSubordinatesTree(Map<Integer, Set<EmployeeDto>> subordinates,
                                      StaffNode staff) {
        Set<EmployeeDto> subordinateDtos = subordinates.get(staff.getEmployee().employeeId());
        if (Objects.isNull(subordinateDtos)) {
            return;
        }
        subordinateDtos.stream()
                .map(employeeDto -> new StaffNode(
                        new Employee(employeeDto.id(),
                                employeeDto.firstName(),
                                employeeDto.lastName(),
                                employeeDto.salary()),
                        staff))
                .forEach(currentEmployee -> {
                    staff.addSubordinate(currentEmployee);
                    fillSubordinatesTree(subordinates, currentEmployee);
                });
    }
}
