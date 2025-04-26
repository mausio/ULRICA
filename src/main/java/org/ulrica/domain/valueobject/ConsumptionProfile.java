package org.ulrica.domain.valueobject;

import java.util.Objects;

public final class ConsumptionProfile {
    private final double consumptionAt50Kmh;
    private final double consumptionAt100Kmh;
    private final double consumptionAt130Kmh;

    public ConsumptionProfile(double consumptionAt50Kmh, double consumptionAt100Kmh, double consumptionAt130Kmh) {
        if (consumptionAt50Kmh <= 0 || consumptionAt100Kmh <= 0 || consumptionAt130Kmh <= 0) {
            throw new IllegalArgumentException("Consumption values must be positive");
        }
        this.consumptionAt50Kmh = consumptionAt50Kmh;
        this.consumptionAt100Kmh = consumptionAt100Kmh;
        this.consumptionAt130Kmh = consumptionAt130Kmh;
    }

    public double getConsumptionAt50Kmh() {
        return consumptionAt50Kmh;
    }

    public double getConsumptionAt100Kmh() {
        return consumptionAt100Kmh;
    }

    public double getConsumptionAt130Kmh() {
        return consumptionAt130Kmh;
    }

    public double getAverageConsumption() {
        return (consumptionAt50Kmh + consumptionAt100Kmh + consumptionAt130Kmh) / 3;
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (object == null || getClass() != object.getClass()) return false;
        ConsumptionProfile that = (ConsumptionProfile) object;
        return Double.compare(that.consumptionAt50Kmh, consumptionAt50Kmh) == 0 &&
                Double.compare(that.consumptionAt100Kmh, consumptionAt100Kmh) == 0 &&
                Double.compare(that.consumptionAt130Kmh, consumptionAt130Kmh) == 0;
    }

    @Override
    public int hashCode() {
        return Objects.hash(consumptionAt50Kmh, consumptionAt100Kmh, consumptionAt130Kmh);
    }

    @Override
    public String toString() {
        return "Consumption Profile: " +
               "15.0, 20.0, 25.0, " +
               "Average Consumption: " + getAverageConsumption();
    }
} 