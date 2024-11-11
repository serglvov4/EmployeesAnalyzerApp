package org.example.employees.analyzer;

import org.example.employees.analyzer.exceptions.CsvFileParsingException;

import java.io.IOException;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import java.util.HashSet;
import java.util.Set;

class CsvTestFileGenerator {
    private static final int NUMBER_OF_RECORDS = 1000;

    public static void main(String[] args) {
        if(args.length == 0) {
            throw new CsvFileParsingException("Path to the CSV file should be provided.");
        }
        Set<Long> correctManagerIds = new HashSet<>();
        try (PrintWriter writer = new PrintWriter(args[0], StandardCharsets.UTF_8)){
            writer.println("Id,firstName,lastName,salary,managerId");
            long ceoId = 1L;
            writer.println(ceoId + ",Joe,Doe,60000, ");
            correctManagerIds.add(ceoId);
            for(int i = 2; i <= NUMBER_OF_RECORDS; i++) {
                long managerId;
                 do {
                    managerId = Math.round(Math.random()*((double) NUMBER_OF_RECORDS / 5));
                    if(managerId == 0) {
                        managerId = ceoId;
                    }
                } while (managerId == i || !correctManagerIds.contains(managerId));
                writer.println(i + ",FirstName" + i +
                        ",LastName" + i + "," +
                        Math.round(Math.random() * 100000) + "," +
                        managerId);
                correctManagerIds.add((long)i);
            }
        } catch (IOException exception) {
            throw new CsvFileParsingException("Can't generate test CSV file: " + exception.getMessage());
        }
    }
}