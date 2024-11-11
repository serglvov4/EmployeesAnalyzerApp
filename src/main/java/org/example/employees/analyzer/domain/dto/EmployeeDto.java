package org.example.employees.analyzer.domain.dto;

import java.math.BigDecimal;

/**
 * DTO record Employee DTO
 * @param id Employee's identifier
 * @param firstName Employee's first name
 * @param lastName Employee's last name
 * @param salary Employee's salary
 * @param managerId Employee manager's identifier
 */
public record EmployeeDto(Integer id, String firstName, String lastName, BigDecimal salary, Integer managerId) {

}
