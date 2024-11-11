package org.example.employees.analyzer.services;

import java.math.BigDecimal;

/**
 * Contract for class to obtain application's properties
 */
public interface PropertiesProvider {

    /**
     * Get minimal salary rate limit
     * @return minimal salary rate limit
     */
    BigDecimal getMinimalSalaryRateLimit();

    /**
     * Get maximal salary rate limit
     * @return maximal salary rate limit
     */
    BigDecimal getMaximalSalaryRateLimit();

    /**
     * Get maximal reporting line length
     * @return maximal reporting line length
     */
    Integer getMaximalReportingLineLength();
}
