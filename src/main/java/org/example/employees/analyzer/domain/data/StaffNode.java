package org.example.employees.analyzer.domain.data;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

public class StaffNode {
    private final Employee employee;
    private final StaffNode manager;
    private final Set<StaffNode> subordinates = new HashSet<>();

    public StaffNode(Employee employee, StaffNode manager) {
        this.employee = employee;
        this.manager = manager;
    }

    /**
     * Get employee's manager
     * @return employee's manager
     */
    public StaffNode getManager() {
        return manager;
    }

    /**
     * Get set of direct subordinates
     * @return set of direct subordinates
     */
    public Set<StaffNode> getSubordinates() {
        return Set.copyOf(subordinates);
    }

    /**
     * Get Employee's data
     * @return Employee's data
     */
    public Employee getEmployee() {
        return employee;
    }

    /**
     * Add subordinate
     * @param subordinate The direct subordinate
     */
    public void addSubordinate(StaffNode subordinate) {
        subordinates.add(subordinate);
    }

    /**
     * Get employee's hierarchical level
     * @return employee's hierarchical level
     */
    public Integer getHierarchyLevel() {
        int hierarchyLevel = 0;
        for(StaffNode currentEmployee = this; Objects.nonNull(currentEmployee.getManager()); currentEmployee = currentEmployee.getManager()) {
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
                .map(staff -> staff.getEmployee().salary())
                .reduce(BigDecimal.ZERO, BigDecimal::add)
                .divide(BigDecimal.valueOf(getSubordinates().size()), 2, RoundingMode.HALF_UP);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        StaffNode staffNode = (StaffNode) o;
        return Objects.equals(employee, staffNode.employee)
                && Objects.equals(
                    manager != null ? manager.getEmployee() : null,
                    staffNode.manager != null ? staffNode.manager.getEmployee() : null
                )
                && Objects.equals(
                    subordinates.stream()
                            .map(StaffNode::getEmployee)
                            .collect(Collectors.toUnmodifiableSet()),
                    staffNode.subordinates.stream()
                            .map(StaffNode::getEmployee)
                            .collect(Collectors.toUnmodifiableSet())
                );
    }

    @Override
    public int hashCode() {
        return Objects.hash(
                employee,
                manager != null ? manager.getEmployee() : null);
    }
}
