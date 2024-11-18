package org.example.employees.analyzer;

import org.example.employees.analyzer.domain.data.StaffNode;
import org.example.employees.analyzer.domain.dto.EmployeesDto;
import org.example.employees.analyzer.services.*;
import org.example.employees.analyzer.services.analyzers.EmployeeAnalyzer;
import org.example.employees.analyzer.services.analyzers.impl.ReportingLineLengthAnalyzerImpl;
import org.example.employees.analyzer.services.analyzers.impl.SalaryAnalyzerImpl;
import org.example.employees.analyzer.services.impl.*;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Run analytical report about employees
 */
public class EmployeesAnalyzerRunner implements Runnable {
    private final String filePath;
    private final PropertiesProvider propertiesProvider;
    private final EmployeesReporter employeesReporter;
    private final HierarchyValidator hierarchyValidator;
    private final EmployeesTreeBuilder employeesTreeBuilder;
    private final EmployeeDtoValidator employeeDtoValidator;
    private final EmployeeFieldsParser employeeFieldsParser;
    private final EmployeesAnalyzer employeesAnalyzer;

    private static final Logger LOGGER = Logger.getLogger(EmployeesAnalyzerRunner.class.getName());

    public EmployeesAnalyzerRunner(String filePath) {
        this.filePath = filePath;
        this.propertiesProvider = new PropertiesProviderImpl();
        this.employeesReporter = new ConsoleEmployeesReporterImpl();
        this.hierarchyValidator = new HierarchyValidatorImpl();
        this.employeesTreeBuilder = new EmployeesTreeBuilderImpl();
        this.employeeDtoValidator = new EmployeeDtoValidatorImpl();
        this.employeeFieldsParser = new EmployeeFieldsParserImpl();
        this.employeesAnalyzer = configureEmployeesAnalyzer();
    }

    /**
     * Run analytical report about employees
     */
    @Override
    public void run() {
        try {
            StaffNode ceoEmployeeTree = buildCeoEmployeeTree();
            analyse(ceoEmployeeTree);
        } catch (Exception exception) {
            LOGGER.log(Level.SEVERE, exception.getMessage());
        }
    }

    /**
     * Analyze report data
     * @param ceoEmployeeTree Report data (Employees tree)
     */
    private void analyse(StaffNode ceoEmployeeTree) {
        employeesAnalyzer.analyze(ceoEmployeeTree);
    }

    /**
     * Configure Employees Analyzer
     * @return Configured Employees Analyzer
     */
    private EmployeesAnalyzer configureEmployeesAnalyzer() {
        List<EmployeeAnalyzer> analyzers = List.of(
                new SalaryAnalyzerImpl(propertiesProvider),
                new ReportingLineLengthAnalyzerImpl(propertiesProvider)
        );
        return new EmployeesAnalyzerImpl(employeesReporter, analyzers);
    }

    /**
     * Build report data
     * @return Employees tree
     */
    private StaffNode buildCeoEmployeeTree() {
        EmployeesDto employeesDto = parseEmployeesDto();
        StaffNode ceoEmployeeTree = employeesTreeBuilder.build(employeesDto);
        hierarchyValidator.validate(ceoEmployeeTree, employeesDto);
        return ceoEmployeeTree;
    }

    /**
     * Parse Employees DTO
     * @return Employees DTO
     */
    private EmployeesDto parseEmployeesDto() {
        CsvFileParser fileParser = new CsvFileParserImpl(employeeDtoValidator, employeeFieldsParser);
        return fileParser.parse(filePath);
    }
}
