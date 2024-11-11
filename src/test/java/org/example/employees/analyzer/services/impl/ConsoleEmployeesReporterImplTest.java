package org.example.employees.analyzer.services.impl;

import org.example.employees.analyzer.services.EmployeesReporter;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.Arrays;
import java.util.List;

class ConsoleEmployeesReporterImplTest {
    private final EmployeesReporter employeesReporter = new ConsoleEmployeesReporterImpl();
    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    private final PrintStream originalOut = System.out;

    @BeforeEach
    public void setUpStreams() {
        System.setOut(new PrintStream(outContent));
    }

    @AfterEach
    public void restoreStreams() {
        System.setOut(originalOut);
    }

    @Test
    void report() {
        Integer employeeId = 123;
        String firstName = "Joe";
        String lastName = "Doe";
        List<String> messages = Arrays.asList("Message 1", "Message 2");
        employeesReporter.report(employeeId, firstName, lastName, messages);
        String result = "Employee Joe Doe (Id = 123):" +
                System.lineSeparator() +
                "   Message 1" +
                System.lineSeparator() +
                "   Message 2" +
                System.lineSeparator() + System.lineSeparator();
        Assertions.assertEquals(result, outContent.toString());
    }

    @Test
    void printTitle() {
        employeesReporter.printTitle();
        String result = "====================================================================" +
                System.lineSeparator() +
                "                ANALYTICAL REPORT ON PERSONNEL" +
                System.lineSeparator() +
                "====================================================================" +
                System.lineSeparator();
        Assertions.assertEquals(result, outContent.toString());
    }

    @Test
    void printFooter() {
        employeesReporter.printFooter();
        String result = "====================================================================" +
                System.lineSeparator();
        Assertions.assertEquals(result, outContent.toString());
    }
}