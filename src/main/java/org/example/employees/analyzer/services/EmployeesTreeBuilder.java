package org.example.employees.analyzer.services;

import org.example.employees.analyzer.domain.data.Employee;
import org.example.employees.analyzer.domain.dto.EmployeesDto;

/**
 * Contract for class to build employee tree structure
 */
public interface EmployeesTreeBuilder {

    /**
     * Build employee tree (hierarchical data structure) using Employees DTO collection
     * @param employeesDto Employees DTO collection
     * @return Employee tree - hierarchical data structure
     */
    Employee build(EmployeesDto employeesDto);
}
