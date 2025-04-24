package org.ulrica.domain.valueobject;

import java.util.Map;
import java.util.Objects;
import java.util.TreeMap;

public final class ChargingCurve {
    private final Map<Double, Double> curvePoints; // battery percentage -> charging power

    public ChargingCurve(Map<Double, Double> curvePoints) {
        if (curvePoints == null || curvePoints.isEmpty()) {
            throw new IllegalArgumentException("Charging curve must have at least one point");
        }
        for (Map.Entry<Double, Double> entry : curvePoints.entrySet()) {
            if (entry.getKey() < 0 || entry.getKey() > 100) {
                throw new IllegalArgumentException("Battery percentage must be between 0 and 100");
            }
            if (entry.getValue() <= 0) {
                throw new IllegalArgumentException("Charging power must be positive");
            }
        }
        this.curvePoints = new TreeMap<>(curvePoints);
    }

    public double getChargingPowerAt(double batteryPercentage) {
        if (batteryPercentage < 0 || batteryPercentage > 100) {
            throw new IllegalArgumentException("Battery percentage must be between 0 and 100");
        }

        Map.Entry<Double, Double> floor = ((TreeMap<Double, Double>) curvePoints).floorEntry(batteryPercentage);
        Map.Entry<Double, Double> ceiling = ((TreeMap<Double, Double>) curvePoints).ceilingEntry(batteryPercentage);

        if (floor == null) {
            return ceiling.getValue();
        }
        if (ceiling == null) {
            return floor.getValue();
        }
        if (floor.getKey().equals(ceiling.getKey())) {
            return floor.getValue();
        }

        // Linear interpolation
        double x1 = floor.getKey();
        double y1 = floor.getValue();
        double x2 = ceiling.getKey();
        double y2 = ceiling.getValue();

        return y1 + (y2 - y1) * (batteryPercentage - x1) / (x2 - x1);
    }

    public Map<Double, Double> getCurvePoints() {
        return Map.copyOf(curvePoints);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ChargingCurve that = (ChargingCurve) o;
        return curvePoints.equals(that.curvePoints);
    }

    @Override
    public int hashCode() {
        return Objects.hash(curvePoints);
    }
} 