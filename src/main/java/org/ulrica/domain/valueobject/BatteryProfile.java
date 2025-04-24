package org.ulrica.domain.valueobject;

import java.util.Objects;

public final class BatteryProfile {
    private final BatteryType type;
    private final double capacityKwh;
    private final double degradationPercent;
    private final double maxDcPowerKw;
    private final double maxAcPowerKw;

    public BatteryProfile(BatteryType type, double capacityKwh, double degradationPercent, double maxDcPowerKw, double maxAcPowerKw) {
        if (capacityKwh <= 0) {
            throw new IllegalArgumentException("Battery capacity must be positive");
        }
        if (degradationPercent < 0 || degradationPercent > 100) {
            throw new IllegalArgumentException("Degradation must be between 0 and 100 percent");
        }
        if (maxDcPowerKw <= 0) {
            throw new IllegalArgumentException("Max DC power must be positive");
        }
        if (maxAcPowerKw <= 0) {
            throw new IllegalArgumentException("Max AC power must be positive");
        }
        this.type = Objects.requireNonNull(type, "Battery type cannot be null");
        this.capacityKwh = capacityKwh;
        this.degradationPercent = degradationPercent;
        this.maxDcPowerKw = maxDcPowerKw;
        this.maxAcPowerKw = maxAcPowerKw;
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

    public double getMaxDcPowerKw() {
        return maxDcPowerKw;
    }

    public double getMaxAcPowerKw() {
        return maxAcPowerKw;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BatteryProfile that = (BatteryProfile) o;
        return Double.compare(that.capacityKwh, capacityKwh) == 0 &&
                Double.compare(that.degradationPercent, degradationPercent) == 0 &&
                Double.compare(that.maxDcPowerKw, maxDcPowerKw) == 0 &&
                Double.compare(that.maxAcPowerKw, maxAcPowerKw) == 0 &&
                type == that.type;
    }

    @Override
    public int hashCode() {
        return Objects.hash(type, capacityKwh, degradationPercent, maxDcPowerKw, maxAcPowerKw);
    }

    @Override
    public String toString() {
        return String.format(
            "Type: %s (%s)%n" +
            "Capacity: %.1f kWh%n" +
            "Degradation: %.1f%%%n" +
            "Remaining Capacity: %.1f kWh%n" +
            "Max DC Power: %.1f kW%n" +
            "Max AC Power: %.1f kW",
            type.name(), type.getDescription(),
            capacityKwh, degradationPercent,
            getRemainingCapacityKwh(),
            maxDcPowerKw, maxAcPowerKw
        );
    }
} 