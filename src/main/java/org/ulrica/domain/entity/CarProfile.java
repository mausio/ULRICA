package org.ulrica.domain.entity;

import java.util.Objects;
import java.util.Optional;

import org.ulrica.domain.valueobject.BatteryProfile;
import org.ulrica.domain.valueobject.ChargingCurve;
import org.ulrica.domain.valueobject.ConsumptionProfile;

public class CarProfile {
    private final String id;
    private final String name;
    private final String manufacturer;
    private final String model;
    private final int year;
    private final boolean hasHeatPump;
    private final double wltpRangeKm;
    private final double maxDcPowerKw;
    private final double maxAcPowerKw;
    private final BatteryProfile batteryProfile;
    private final ConsumptionProfile consumptionProfile;
    private final Optional<ChargingCurve> chargingCurve;

    private CarProfile(Builder builder) {
        this.id = builder.id;
        this.name = Objects.requireNonNull(builder.name, "Name cannot be null");
        this.manufacturer = Objects.requireNonNull(builder.manufacturer, "Manufacturer cannot be null");
        this.model = Objects.requireNonNull(builder.model, "Model cannot be null");
        this.year = builder.year;
        this.hasHeatPump = builder.hasHeatPump;
        this.wltpRangeKm = builder.wltpRangeKm;
        this.maxDcPowerKw = builder.maxDcPowerKw;
        this.maxAcPowerKw = builder.maxAcPowerKw;
        this.batteryProfile = Objects.requireNonNull(builder.batteryProfile, "Battery profile cannot be null");
        this.consumptionProfile = Objects.requireNonNull(builder.consumptionProfile, "Consumption profile cannot be null");
        this.chargingCurve = Optional.ofNullable(builder.chargingCurve);
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getManufacturer() {
        return manufacturer;
    }

    public String getModel() {
        return model;
    }

    public int getYear() {
        return year;
    }

    public boolean hasHeatPump() {
        return hasHeatPump;
    }

    public double getWltpRangeKm() {
        return wltpRangeKm;
    }

    public double getMaxDcPowerKw() {
        return maxDcPowerKw;
    }

    public double getMaxAcPowerKw() {
        return maxAcPowerKw;
    }

    public BatteryProfile getBatteryProfile() {
        return batteryProfile;
    }

    public ConsumptionProfile getConsumptionProfile() {
        return consumptionProfile;
    }

    public Optional<ChargingCurve> getChargingCurve() {
        return chargingCurve;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CarProfile that = (CarProfile) o;
        return year == that.year &&
                hasHeatPump == that.hasHeatPump &&
                Double.compare(that.wltpRangeKm, wltpRangeKm) == 0 &&
                Double.compare(that.maxDcPowerKw, maxDcPowerKw) == 0 &&
                Double.compare(that.maxAcPowerKw, maxAcPowerKw) == 0 &&
                Objects.equals(id, that.id) &&
                Objects.equals(name, that.name) &&
                Objects.equals(manufacturer, that.manufacturer) &&
                Objects.equals(model, that.model) &&
                Objects.equals(batteryProfile, that.batteryProfile) &&
                Objects.equals(consumptionProfile, that.consumptionProfile) &&
                Objects.equals(chargingCurve, that.chargingCurve);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, manufacturer, model, year, hasHeatPump, wltpRangeKm, maxDcPowerKw, maxAcPowerKw, batteryProfile, consumptionProfile, chargingCurve);
    }

    @Override
    public String toString() {
        return String.format(
            "Car Profile: %s%n" +
            "Manufacturer: %s%n" +
            "Model: %s%n" +
            "Year: %d%n" +
            "Heat Pump: %s%n" +
            "WLTP Range: %.1f km%n" +
            "Max DC Power: %.1f kW%n" +
            "Max AC Power: %.1f kW%n" +
            "Battery: %s%n" +
            "Consumption Profile: %s",
            name, manufacturer, model, year, hasHeatPump ? "Yes" : "No",
            wltpRangeKm, maxDcPowerKw, maxAcPowerKw,
            batteryProfile.toString(), consumptionProfile.toString()
        );
    }

    public static class Builder {
        private String id;
        private String name;
        private String manufacturer;
        private String model;
        private int year;
        private boolean hasHeatPump;
        private double wltpRangeKm;
        private double maxDcPowerKw;
        private double maxAcPowerKw;
        private BatteryProfile batteryProfile;
        private ConsumptionProfile consumptionProfile;
        private ChargingCurve chargingCurve;

        public Builder id(String id) {
            this.id = id;
            return this;
        }

        public Builder name(String name) {
            this.name = name;
            return this;
        }

        public Builder manufacturer(String manufacturer) {
            this.manufacturer = manufacturer;
            return this;
        }

        public Builder model(String model) {
            this.model = model;
            return this;
        }

        public Builder year(int year) {
            this.year = year;
            return this;
        }

        public Builder hasHeatPump(boolean hasHeatPump) {
            this.hasHeatPump = hasHeatPump;
            return this;
        }

        public Builder wltpRangeKm(double wltpRangeKm) {
            this.wltpRangeKm = wltpRangeKm;
            return this;
        }

        public Builder maxDcPowerKw(double maxDcPowerKw) {
            this.maxDcPowerKw = maxDcPowerKw;
            return this;
        }

        public Builder maxAcPowerKw(double maxAcPowerKw) {
            this.maxAcPowerKw = maxAcPowerKw;
            return this;
        }

        public Builder batteryProfile(BatteryProfile batteryProfile) {
            this.batteryProfile = batteryProfile;
            return this;
        }

        public Builder consumptionProfile(ConsumptionProfile consumptionProfile) {
            this.consumptionProfile = consumptionProfile;
            return this;
        }

        public Builder chargingCurve(ChargingCurve chargingCurve) {
            this.chargingCurve = chargingCurve;
            return this;
        }

        public CarProfile build() {
            return new CarProfile(this);
        }
    }
} 