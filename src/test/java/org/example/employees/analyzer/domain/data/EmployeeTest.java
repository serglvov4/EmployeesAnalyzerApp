package org.example.employees.analyzer.domain.data;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.Comparator;

class EmployeeTest {
    private static Employee employee;

    @BeforeAll
    static void setUp() {
        employee = new EmployeeProvider().provide();
    }

    @Test
    void getHierarchyLevel() {
        Assertions.assertNotNull(employee);
        Assertions.assertEquals("Joe", employee.getFirstName());
        Assertions.assertEquals("Doe", employee.getLastName());
        Assertions.assertEquals(0, employee.getHierarchyLevel());
        Assertions.assertEquals(2, employee.getSubordinates().size());
        employee.getSubordinates().forEach(emp -> Assertions.assertEquals(1, emp.getHierarchyLevel()));
        employee.getSubordinates().stream()
                .max(Comparator.comparing(o -> o.getSubordinates().size()))
                .ifPresent(emp -> {
                    Assertions.assertEquals(2, emp.getSubordinates().size());
                    emp.getSubordinates()
                            .forEach(empl -> Assertions.assertEquals(2, empl.getHierarchyLevel()));
                });
    }

    @Test
    void getAverageSubordinatesSalary() {
        Assertions.assertNotNull(employee);
        Assertions.assertEquals(2,employee.getSubordinates().size());
        Assertions.assertEquals(new BigDecimal("46000.00"), employee.getAverageSubordinatesSalary());
        var subordinates =  employee.getSubordinates().stream()
                .sorted(Comparator.comparing(o -> o.getSubordinates().size()))
                .toList();
        Assertions.assertEquals(2, subordinates.size());
        var firstSubordinate = subordinates.getFirst();
        Assertions.assertEquals(BigDecimal.ZERO, firstSubordinate.getAverageSubordinatesSalary());
        var secondSubordinate = subordinates.get(1);
        Assertions.assertEquals(new BigDecimal("275003.89"), secondSubordinate.getAverageSubordinatesSalary());
    }

}