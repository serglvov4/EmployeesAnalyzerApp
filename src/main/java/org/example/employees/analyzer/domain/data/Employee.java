package org.example.employees.analyzer.domain.data;

import java.math.BigDecimal;

public record Employee(Integer employeeId, String firstName, String lastName, BigDecimal salary) {

}
