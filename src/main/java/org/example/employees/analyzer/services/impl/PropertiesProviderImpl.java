package org.example.employees.analyzer.services.impl;

import org.example.employees.analyzer.services.PropertiesProvider;

import java.math.BigDecimal;
import java.util.Properties;

/**
 * Application properties provider (uses application.properties file in resources folder)
 */
public class PropertiesProviderImpl implements PropertiesProvider {
    private final BigDecimal minimalSalaryRateLimit;
    private final BigDecimal maximalSalaryRateLimit;
    private final Integer maximalReportingLineLength;

    public PropertiesProviderImpl() {
        this("/application.properties");
    }

    public PropertiesProviderImpl(String fileName) {
        BigDecimal minimalSalaryRateLimitValue = new BigDecimal("1.2");
        BigDecimal maximalSalaryRateLimitValue = new BigDecimal("1.5");
        int maximalReportingLineLengthValue = 4;
        Properties appProps = new Properties();
        try {
            appProps.load(getClass().getResourceAsStream(fileName));
            minimalSalaryRateLimitValue = new BigDecimal(appProps.getProperty("app.minimal-salary-rate-limit"));
            maximalSalaryRateLimitValue = new BigDecimal(appProps.getProperty("app.maximal-salary-rate-limit"));
            maximalReportingLineLengthValue = Integer.parseInt(appProps.getProperty("app.maximal-reporting-line-length"));
        } catch (Exception exception) {
            System.err.println("Default values applied. Can't read application properties");
        }
        this.minimalSalaryRateLimit = minimalSalaryRateLimitValue;
        this.maximalSalaryRateLimit = maximalSalaryRateLimitValue;
        this.maximalReportingLineLength = maximalReportingLineLengthValue;
    }

    /**
     * Get minimal salary rate limit
     * @return minimal salary rate limit
     */
    @Override
    public BigDecimal getMinimalSalaryRateLimit() {
        return minimalSalaryRateLimit;
    }

    /**
     * Get maximal salary rate limit
     * @return maximal salary rate limit
     */
    @Override
    public BigDecimal getMaximalSalaryRateLimit() {
        return maximalSalaryRateLimit;
    }

    /**
     * Get maximal reporting line length
     * @return maximal reporting line length
     */
    @Override
    public Integer getMaximalReportingLineLength() {
        return maximalReportingLineLength;
    }
}
