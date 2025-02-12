package model.strategy.efficiency;

public interface WeatherEfficiencyFactor extends EfficiencyFactor {
    enum WeatherCondition {
        SUNNY(1.0),
        CLOUDY(1.005),
        RAIN(1.01),
        SNOW(1.03),
        STRONG_WIND(1.04);

        private final double baseFactor;

        WeatherCondition(double baseFactor) {
            this.baseFactor = baseFactor;
        }

        public double getBaseFactor() {
            return baseFactor;
        }
    }

    WeatherCondition getWeatherCondition();
    double getTemperature(); // in c°
} 