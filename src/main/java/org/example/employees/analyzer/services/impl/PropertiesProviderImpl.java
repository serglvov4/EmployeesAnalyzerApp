package org.example.employees.analyzer.services.impl;

import org.example.employees.analyzer.services.PropertiesProvider;

import java.math.BigDecimal;
import java.util.Objects;
import java.util.Optional;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Application properties provider (uses application.properties file in resources folder)
 */
public class PropertiesProviderImpl implements PropertiesProvider {
    private static final BigDecimal DEFAULT_MINIMAL_SALARY_RATE_LIMIT = new BigDecimal("1.2");
    private static final BigDecimal DEFAULT_MAXIMAL_SALARY_RATE_LIMIT = new BigDecimal("1.5");
    private static final Integer DEFAULT_MAXIMAL_REPORTING_LINE_LENGTH = 4;

    private static final Logger LOGGER = Logger.getLogger(PropertiesProviderImpl.class.getName());

    private final BigDecimal minimalSalaryRateLimit;
    private final BigDecimal maximalSalaryRateLimit;
    private final Integer maximalReportingLineLength;

    public PropertiesProviderImpl() {
        this("/application.properties");
    }

    public PropertiesProviderImpl(String fileName) {
        Properties appProps = new Properties();
        try {
            appProps.load(getClass().getResourceAsStream(fileName));
        } catch (Exception exception) {
            LOGGER.warning("Default values applied. Can't read application properties");
            this.minimalSalaryRateLimit = DEFAULT_MINIMAL_SALARY_RATE_LIMIT;
            this.maximalSalaryRateLimit = DEFAULT_MAXIMAL_SALARY_RATE_LIMIT;
            this.maximalReportingLineLength = DEFAULT_MAXIMAL_REPORTING_LINE_LENGTH;
            return;
        }
        minimalSalaryRateLimit = getPropertyValue("app.minimal-salary-rate-limit", appProps)
                .orElse(DEFAULT_MINIMAL_SALARY_RATE_LIMIT);
        maximalSalaryRateLimit = getPropertyValue("app.maximal-salary-rate-limit", appProps)
                .orElse(DEFAULT_MAXIMAL_SALARY_RATE_LIMIT);
        maximalReportingLineLength = getPropertyValue("app.maximal-reporting-line-length", appProps)
                .map(BigDecimal::intValue)
                .orElse(DEFAULT_MAXIMAL_REPORTING_LINE_LENGTH);
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

    /**
     * Get application property value
     * @param propertyName Name of property
     * @param appProps Set of application properties
     * @return Property value
     */
    private Optional<BigDecimal> getPropertyValue(String propertyName, Properties appProps) {
        BigDecimal value = null;
        String propertyValue = appProps.getProperty(propertyName);
        if (Objects.nonNull(propertyValue)) {
            try {
                value = new BigDecimal(propertyValue);
            } catch (NumberFormatException ignored) {
                LOGGER.log(Level.WARNING, "Invalid value for property {0} ({1}). Default value applied.",
                        new Object[] { propertyName, propertyValue } );
            }
        } else {
            LOGGER.log(Level.WARNING, "Can''t find value for property {0}. Default value applied.", propertyName);
        }
        return Optional.ofNullable(value);
    }
}
