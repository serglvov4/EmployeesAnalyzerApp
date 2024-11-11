package org.example.employees.analyzer.services;

import java.math.BigDecimal;

/**
 * Contract for class to validate and parse fields string values from CSV file
 */
public interface EmployeeFieldsParser {

    /**
     * Validate and parse Employee ID value from CSV file
     * @param employeeId Employee ID string value
     * @return Employee ID
     */
    Integer parseEmployeeId(String employeeId);

    /**
     * Validate and parse First Name value from CSV file
     * @param firstName First Name string value
     * @return First Name
     */
    String parseFirstName(String firstName);

    /**
     * Validate and parse Last Name value from CSV file
     * @param lastName Last Name string value
     * @return Last Name
     */
    String parseLastName(String lastName);

    /**
     * Validate and parse Salary value from CSV file
     * @param salary Salary string value
     * @return Salary
     */
    BigDecimal parseSalary(String salary);

    /**
     * Validate and parse Manager ID value from CSV file
     * @param managerId Manager ID string value
     * @return Manager ID
     */
    Integer parseManagerId(String managerId);
}
