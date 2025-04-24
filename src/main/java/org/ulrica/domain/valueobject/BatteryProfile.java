package org.ulrica.domain.valueobject;

import java.util.Objects;

public final class BatteryProfile {
    private final BatteryType type;
    private final double capacityKwh;
    private final double degradationPercent;

    public BatteryProfile(BatteryType type, double capacityKwh, double degradationPercent) {
        if (capacityKwh <= 0) {
            throw new IllegalArgumentException("Battery capacity must be positive");
        }
        if (degradationPercent < 0 || degradationPercent > 100) {
            throw new IllegalArgumentException("Degradation must be between 0 and 100 percent");
        }
        this.type = Objects.requireNonNull(type, "Battery type cannot be null");
        this.capacityKwh = capacityKwh;
        this.degradationPercent = degradationPercent;
    }

    public BatteryType getType() {
        return type;
    }

    public double getCapacityKwh() {
        return capacityKwh;
    }

    public double getDegradationPercent() {
        return degradationPercent;
    }

    public double getRemainingCapacityKwh() {
        return capacityKwh * (1 - degradationPercent / 100);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BatteryProfile that = (BatteryProfile) o;
        return Double.compare(that.capacityKwh, capacityKwh) == 0 &&
                Double.compare(that.degradationPercent, degradationPercent) == 0 &&
                type == that.type;
    }

    @Override
    public int hashCode() {
        return Objects.hash(type, capacityKwh, degradationPercent);
    }

    @Override
    public String toString() {
        return String.format(
            "Type: %s (%s)%n" +
            "Capacity: %.1f kWh%n" +
            "Degradation: %.1f%%%n" +
            "Remaining Capacity: %.1f kWh",
            type.name(), type.getDescription(),
            capacityKwh, degradationPercent,
            getRemainingCapacityKwh()
        );
    }
} 