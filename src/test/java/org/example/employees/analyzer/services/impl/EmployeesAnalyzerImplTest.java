package org.example.employees.analyzer.services.impl;

import org.example.employees.analyzer.domain.data.StaffNode;
import org.example.employees.analyzer.domain.data.StaffTreeProvider;
import org.example.employees.analyzer.services.EmployeesAnalyzer;
import org.example.employees.analyzer.services.analyzers.impl.ReportingLineLengthAnalyzerImpl;
import org.example.employees.analyzer.services.analyzers.impl.SalaryAnalyzerImpl;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.List;

class EmployeesAnalyzerImplTest {
    private final EmployeesAnalyzer employeesAnalyzer;
    private final StaffTreeProvider staffTreeProvider;
    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    private final PrintStream originalOut = System.out;

    EmployeesAnalyzerImplTest() {
        this.employeesAnalyzer = new EmployeesAnalyzerImpl(
                new ConsoleEmployeesReporterImpl(), List.of(
                        new SalaryAnalyzerImpl(new PropertiesProviderImpl()),
                        new ReportingLineLengthAnalyzerImpl(new PropertiesProviderImpl())
        ));
        this.staffTreeProvider = new StaffTreeProvider();
    }

    @BeforeEach
    public void setUpStreams() {
        System.setOut(new PrintStream(outContent));
    }

    @AfterEach
    public void restoreStreams() {
        System.setOut(originalOut);
    }

    @Test
    void analyze_success() {
        StaffNode employee = Assertions.assertDoesNotThrow(staffTreeProvider::provide);
        Assertions.assertDoesNotThrow(() -> employeesAnalyzer.analyze(employee));
        String result = "====================================================================" +
                System.lineSeparator() +
                "                ANALYTICAL REPORT ON PERSONNEL" +
                System.lineSeparator() +
                "====================================================================" +
                System.lineSeparator() +
                "Employee Martin Chekov (Id = 124):" +
                System.lineSeparator() +
                "    - earns less than it should be (by 285004.67)" +
                System.lineSeparator() +
                System.lineSeparator() +
                "Employee Brett Hardleaf (Id = 305):" +
                System.lineSeparator() +
                "    - earns more than it should be (by 28000.00)" +
                System.lineSeparator() +
                System.lineSeparator() +
                "Employee John White (Id = 315):" +
                System.lineSeparator() +
                "    - earns less than it should be (by 32004.80)" +
                System.lineSeparator() +
                System.lineSeparator() +
                "Employee Philipp Renoir (Id = 316):" +
                System.lineSeparator() +
                "    - earns less than it should be (by 6000.80)" +
                System.lineSeparator() +
                System.lineSeparator() +
                "Employee Steeve McDumm (Id = 317):" +
                System.lineSeparator() +
                "    - earns less than it should be (by 6000.80)" +
                System.lineSeparator() +
                "    - reporting line is too long (by 1 levels)" +
                System.lineSeparator() +
                System.lineSeparator() +
                "Employee Oliver Hendrue (Id = 318):" +
                System.lineSeparator() +
                "    - reporting line is too long (by 2 levels)" +
                System.lineSeparator() +
                System.lineSeparator() +
                "====================================================================" +
                System.lineSeparator();
        Assertions.assertEquals(result, outContent.toString());
    }

    @Test
    void analyze_withEmployeeIsNull() {
        Assertions.assertDoesNotThrow(() -> employeesAnalyzer.analyze(null));
        String result = "====================================================================" +
                System.lineSeparator() +
                "                ANALYTICAL REPORT ON PERSONNEL" +
                System.lineSeparator() +
                "====================================================================" +
                System.lineSeparator() +
                "====================================================================" +
                System.lineSeparator();
        Assertions.assertEquals(result, outContent.toString());
    }
}