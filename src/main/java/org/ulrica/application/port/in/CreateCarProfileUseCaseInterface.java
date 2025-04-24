package org.ulrica.application.port.in;

import org.ulrica.domain.entity.CarProfile;

public interface CreateCarProfileUseCaseInterface {
    CarProfile createCarProfile(CreateCarProfileCommand command);

    class CreateCarProfileCommand {
        private final String name;
        private final String manufacturer;
        private final String model;
        private final int year;
        private final boolean hasHeatPump;
        private final double wltpRangeKm;
        private final double maxDcPowerKw;
        private final double maxAcPowerKw;
        private final String batteryType;
        private final double batteryCapacityKwh;
        private final double batteryDegradationPercent;
        private final double consumptionAt50Kmh;
        private final double consumptionAt100Kmh;
        private final double consumptionAt130Kmh;

        public CreateCarProfileCommand(
                String name,
                String manufacturer,
                String model,
                int year,
                boolean hasHeatPump,
                double wltpRangeKm,
                double maxDcPowerKw,
                double maxAcPowerKw,
                String batteryType,
                double batteryCapacityKwh,
                double batteryDegradationPercent,
                double consumptionAt50Kmh,
                double consumptionAt100Kmh,
                double consumptionAt130Kmh) {
            this.name = name;
            this.manufacturer = manufacturer;
            this.model = model;
            this.year = year;
            this.hasHeatPump = hasHeatPump;
            this.wltpRangeKm = wltpRangeKm;
            this.maxDcPowerKw = maxDcPowerKw;
            this.maxAcPowerKw = maxAcPowerKw;
            this.batteryType = batteryType;
            this.batteryCapacityKwh = batteryCapacityKwh;
            this.batteryDegradationPercent = batteryDegradationPercent;
            this.consumptionAt50Kmh = consumptionAt50Kmh;
            this.consumptionAt100Kmh = consumptionAt100Kmh;
            this.consumptionAt130Kmh = consumptionAt130Kmh;
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

        public String getBatteryType() {
            return batteryType;
        }

        public double getBatteryCapacityKwh() {
            return batteryCapacityKwh;
        }

        public double getBatteryDegradationPercent() {
            return batteryDegradationPercent;
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
    }
} 