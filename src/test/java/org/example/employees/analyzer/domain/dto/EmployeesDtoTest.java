package org.example.employees.analyzer.domain.dto;

import org.example.employees.analyzer.exceptions.LogicalIntegrityException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.Map;
import java.util.Set;

class EmployeesDtoTest {
    private final EmployeesDtoProvider employeesDtoProvider = new EmployeesDtoProvider();

    @Test
    void addEmployeeDto() {
        EmployeesDto employeesDto = employeesDtoProvider.provide();
        Assertions.assertNotNull(employeesDto);
        Assertions.assertEquals(10, employeesDto.getEmployeesIds().size());
        EmployeeDto employeeDto = new EmployeeDto(200,
                "Alexander",
                "Great",
                new BigDecimal("100000"),
                123);
        Assertions.assertDoesNotThrow(() -> employeesDto.addEmployeeDto(employeeDto));
        Assertions.assertEquals(11, employeesDto.getEmployeesIds().size());
    }

    @Test
    void addEmployeeDtoWithCeoRecord() {
        EmployeesDto employeesDtoWithoutCeoRecord = employeesDtoProvider.provideWithoutCeoRecord();
        Assertions.assertNotNull(employeesDtoWithoutCeoRecord);
        Assertions.assertEquals(9, employeesDtoWithoutCeoRecord.getEmployeesIds().size());
        EmployeeDto ceoDto = new EmployeeDto(123,
                "Joe",
                "Doe",
                new BigDecimal("60000"),
                null);
        Assertions.assertDoesNotThrow(() -> employeesDtoWithoutCeoRecord.addEmployeeDto(ceoDto));
        Assertions.assertEquals(10, employeesDtoWithoutCeoRecord.getEmployeesIds().size());
    }

    @Test
    void addEmployeeDto_withSecondCeoRecord() {
        EmployeesDto employeesDto = employeesDtoProvider.provide();
        Assertions.assertNotNull(employeesDto);
        Assertions.assertEquals(10, employeesDto.getEmployeesIds().size());
        EmployeeDto employeeDto = new EmployeeDto(200,
                "Joe",
                "Doe",
                new BigDecimal("60000"),
                null);
        Assertions.assertThrows(LogicalIntegrityException.class, () -> employeesDto.addEmployeeDto(employeeDto));
    }

    @Test
    void addEmployeeDto_withTheSameId() {
        EmployeesDto employeesDto = employeesDtoProvider.provide();
        Assertions.assertNotNull(employeesDto);
        Assertions.assertEquals(10, employeesDto.getEmployeesIds().size());
        EmployeeDto employeeDto = new EmployeeDto(124,
                "Alexander",
                "Great",
                new BigDecimal("100000"),
                123);
        Assertions.assertThrows(LogicalIntegrityException.class, () -> employeesDto.addEmployeeDto(employeeDto));
    }

    @Test
    void addEmployeeDto_withEmployeeDtoIsNull() {
        EmployeesDto employeesDto = employeesDtoProvider.provide();
        Assertions.assertNotNull(employeesDto);
        Assertions.assertEquals(10, employeesDto.getEmployeesIds().size());
        Assertions.assertDoesNotThrow(() -> employeesDto.addEmployeeDto(null));
        Assertions.assertEquals(10, employeesDto.getEmployeesIds().size());
    }

    @Test
    void getCEO() {
        EmployeesDto employeesDto = employeesDtoProvider.provide();
        Assertions.assertNotNull(employeesDto);
        var ceo = employeesDto.getCEO();
        Assertions.assertTrue(ceo.isPresent());
        Assertions.assertEquals(123, ceo.get().id());
        Assertions.assertNull(ceo.get().managerId());
    }

    @Test
    void getCEO_IfNoCeoInFile() {
        EmployeesDto employeesDto = employeesDtoProvider.provideWithoutCeoRecord();
        Assertions.assertNotNull(employeesDto);
        Assertions.assertFalse(employeesDto.getCEO().isPresent());
    }

    @Test
    void getSubordinates() {
        EmployeesDto employeesDto = employeesDtoProvider.provide();
        Assertions.assertNotNull(employeesDto);
        Map<Integer, Set<EmployeeDto>> subordinates = employeesDto.getSubordinates();
        Assertions.assertNotNull(subordinates);
        Assertions.assertEquals(10 - 1 - 2, subordinates.values().size());
    }

    @Test
    void getEmployeesIds() {
        EmployeesDto employeesDto = employeesDtoProvider.provide();
        Assertions.assertNotNull(employeesDto);
        Assertions.assertEquals(10, employeesDto.getEmployeesIds().size());
    }
}