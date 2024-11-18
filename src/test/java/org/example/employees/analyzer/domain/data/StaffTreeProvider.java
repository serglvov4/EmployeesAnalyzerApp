package org.example.employees.analyzer.domain.data;

import org.example.employees.analyzer.domain.dto.EmployeesDto;
import org.example.employees.analyzer.domain.dto.EmployeesDtoProvider;
import org.example.employees.analyzer.services.EmployeesTreeBuilder;
import org.example.employees.analyzer.services.impl.EmployeesTreeBuilderImpl;

/**
 * Provide correct Employee tree structure
 */
public class StaffTreeProvider {

    /**
     * Provide correct Employee tree structure
     * @return Employee tree structure
     */
    public StaffNode provide() {
        EmployeesDto employeesDto = new EmployeesDtoProvider().provide();
        EmployeesTreeBuilder employeesTreeBuilder = new EmployeesTreeBuilderImpl();
        return employeesTreeBuilder.build(employeesDto);
    }
}
