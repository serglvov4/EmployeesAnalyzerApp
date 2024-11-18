package org.example.employees.analyzer.domain.data;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.Comparator;

class StaffNodeTest {
    private static StaffNode staff;

    @BeforeAll
    static void setUpStaff() {
        staff = new StaffTreeProvider().provide();
    }

    @Test
    void getHierarchyLevel() {
        Assertions.assertNotNull(staff);
        Assertions.assertEquals("Joe", staff.getEmployee().firstName());
        Assertions.assertEquals("Doe", staff.getEmployee().lastName());
        Assertions.assertEquals(0, staff.getHierarchyLevel());
        Assertions.assertEquals(2, staff.getSubordinates().size());
        staff.getSubordinates().forEach(emp -> Assertions.assertEquals(1, emp.getHierarchyLevel()));
        staff.getSubordinates().stream()
                .max(Comparator.comparing(o -> o.getSubordinates().size()))
                .ifPresent(emp -> {
                    Assertions.assertEquals(2, emp.getSubordinates().size());
                    emp.getSubordinates()
                            .forEach(empl -> Assertions.assertEquals(2, empl.getHierarchyLevel()));
                });
    }

    @Test
    void getAverageSubordinatesSalary() {
        Assertions.assertNotNull(staff);
        Assertions.assertEquals(2, staff.getSubordinates().size());
        Assertions.assertEquals(new BigDecimal("46000.00"), staff.getAverageSubordinatesSalary());
        var subordinates =  staff.getSubordinates().stream()
                .sorted(Comparator.comparing(o -> o.getSubordinates().size()))
                .toList();
        Assertions.assertEquals(2, subordinates.size());
        var firstSubordinate = subordinates.getFirst();
        Assertions.assertEquals(BigDecimal.ZERO, firstSubordinate.getAverageSubordinatesSalary());
        var secondSubordinate = subordinates.get(1);
        Assertions.assertEquals(new BigDecimal("275003.89"), secondSubordinate.getAverageSubordinatesSalary());
    }

    @Test
    void equals() {
        StaffNode ceo1 = new StaffNode(
                new Employee(staff.getEmployee().employeeId(),
                        staff.getEmployee().firstName(),
                        staff.getEmployee().lastName(),
                        staff.getEmployee().salary()), null);
        Assertions.assertNotEquals(staff, ceo1);
        StaffNode ceo2 = new StaffNode(
                new Employee(staff.getEmployee().employeeId(),
                        staff.getEmployee().firstName(),
                        staff.getEmployee().lastName(),
                        staff.getEmployee().salary()), null);
        Assertions.assertEquals(ceo1, ceo2);
        Assertions.assertEquals(ceo1.hashCode(), ceo2.hashCode());
        StaffNode subordinate1 = new StaffNode(
                new Employee(124, "Martin", "Chekov", new BigDecimal("45000")),
                ceo1
        );
        ceo1.addSubordinate(subordinate1);
        Assertions.assertNotEquals(ceo1, ceo2);
        StaffNode subordinate2 = new StaffNode(
                new Employee(124, "Martin", "Chekov", new BigDecimal("45000")),
                ceo2
        );
        ceo2.addSubordinate(subordinate2);
        Assertions.assertEquals(ceo1, ceo2);
        Assertions.assertEquals(ceo1.hashCode(), ceo2.hashCode());
        Assertions.assertEquals(subordinate1, subordinate2);
        Assertions.assertEquals(subordinate1.hashCode(), subordinate2.hashCode());
    }

}