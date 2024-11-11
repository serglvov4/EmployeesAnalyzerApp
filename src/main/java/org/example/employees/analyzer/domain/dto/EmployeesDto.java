package org.example.employees.analyzer.domain.dto;

import org.example.employees.analyzer.exceptions.LogicalIntegrityException;

import java.util.*;
import java.util.stream.Collectors;

public class EmployeesDto {
    private final Map<Integer, EmployeeDto> employees = new HashMap<>();

    /**
     * Add employee DTO to the employees DTO set
     * @param employeeDto Employee DTO to add to the employees DTO collection
     */
    public void addEmployeeDto(EmployeeDto employeeDto) {
        if (Objects.nonNull(employeeDto)) {
            if(employees.containsKey(employeeDto.id())) {
                throw new LogicalIntegrityException("Only one record with the same id should be present in file. Id = " + employeeDto.id());
            }
            if(Objects.isNull(employeeDto.managerId()) && getCEO().isPresent()) {
                throw new LogicalIntegrityException("Only one record with CEO record should be present in file. Id = " + employeeDto.id());
            }
            employees.put(employeeDto.id(), employeeDto);
        }
    }

    /**
     * Get employee DTO for employee with CEO position
     * @return employee DTO for employee with CEO position
     */
    public Optional<EmployeeDto> getCEO() {
        return employees.values().stream()
                .filter(employeeDto -> Objects.isNull(employeeDto.managerId()))
                .findFirst();
    }

    /**
     * Get employees DTO collection grouped by manager id
     * @return employees DTO collection grouped by manager id
     */
    public Map<Integer, Set<EmployeeDto>> getSubordinates() {
        return employees.values().stream()
                .filter(employeeDto -> Objects.nonNull(employeeDto.managerId()))
                .collect(Collectors.groupingBy(EmployeeDto::managerId,
                        Collectors.mapping(employeeDto -> employeeDto, Collectors.toUnmodifiableSet())));
    }

    /**
     * Get all ids of employees
     * @return set of employees' ids
     */
    public Set<Integer> getEmployeesIds() {
        return Set.copyOf(employees.keySet());
    }
}
