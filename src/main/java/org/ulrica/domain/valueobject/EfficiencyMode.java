package org.ulrica.domain.valueobject;

public enum EfficiencyMode {
    ECO("Optimized for maximum range", 0.95),
    NORMAL("Balanced performance and range", 1.0),
    SPORT("Prioritized performance over range", 1.1);

    private final String description;
    private final double consumptionFactor;

    EfficiencyMode(String description, double consumptionFactor) {
        this.description = description;
        this.consumptionFactor = consumptionFactor;
    }

    public String getDescription() {
        return description;
    }

    public double getConsumptionFactor() {
        return consumptionFactor;
    }
} 