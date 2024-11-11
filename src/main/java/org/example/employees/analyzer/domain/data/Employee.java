package org.example.employees.analyzer.domain.data;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

public class Employee {
    private final Integer employeeId;
    private final String firstName;
    private final String lastName;
    private final BigDecimal salary;
    private final Employee manager;
    private final Set<Employee> subordinates = new HashSet<>();

    public Employee(Integer employeeId, String firstName, String lastName,
                    BigDecimal salary, Employee manager) {
        this.employeeId = employeeId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.salary = salary;
        this.manager = manager;
    }

    /**
     * Add subordinate
     * @param subordinate The direct subordinate
     */
    public void addSubordinate(Employee subordinate) {
        subordinates.add(subordinate);
    }

    /**
     * Get set of direct subordinates
     * @return set of direct subordinates
     */
    public Set<Employee> getSubordinates() {
        return Set.copyOf(subordinates);
    }

    /**
     * Get employee's id
     * @return employee's id
     */
    public Integer getEmployeeId() {
        return employeeId;
    }

    /**
     * Get employee's first name
     * @return employee's first name
     */
    public String getFirstName() {
        return firstName;
    }

    /**
     * Get employee's last name
     * @return employee's last name
     */
    public String getLastName() {
        return lastName;
    }

    /**
     * Get employee's manager
     * @return employee's manager
     */
    public Employee getManager() {
        return manager;
    }

    /**
     * Get employee's salary
     * @return employee's salary
     */
    public BigDecimal getSalary() {
        return salary;
    }

    /**
     * Get employee's hierarchical level
     * @return employee's hierarchical level
     */
    public Integer getHierarchyLevel() {
        int hierarchyLevel = 0;
        for(Employee currentEmployee = this; Objects.nonNull(currentEmployee.getManager()); currentEmployee = currentEmployee.getManager()) {
            hierarchyLevel++;
        }
        return hierarchyLevel;
    }

    /**
     * Get average employee's subordinates' salary
     * @return average employee's subordinates' salary
     */
    public BigDecimal getAverageSubordinatesSalary() {
        return subordinates.isEmpty() ? BigDecimal.ZERO : getSubordinates().stream()
                .map(Employee::getSalary)
                .reduce(BigDecimal.ZERO, BigDecimal::add)
                .divide(BigDecimal.valueOf(getSubordinates().size()), 2, RoundingMode.HALF_UP);
    }
}
