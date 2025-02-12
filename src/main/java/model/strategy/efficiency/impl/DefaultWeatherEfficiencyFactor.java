package model.strategy.efficiency.impl;

import model.strategy.efficiency.WeatherEfficiencyFactor;

public class DefaultWeatherEfficiencyFactor implements WeatherEfficiencyFactor {
    private final WeatherCondition weatherCondition;
    private final double temperature;

    public DefaultWeatherEfficiencyFactor(WeatherCondition weatherCondition, double temperature) {
        validateWeatherCondition(weatherCondition, temperature);
        this.weatherCondition = weatherCondition;
        this.temperature = temperature;
    }

    private void validateWeatherCondition(WeatherCondition condition, double temperature) {
        if (condition == WeatherCondition.SNOW && temperature > 5) {
            throw new IllegalArgumentException("Snow condition is only valid for temperatures below 5°C");
        }
    }

    @Override
    public WeatherCondition getWeatherCondition() {
        return weatherCondition;
    }

    @Override
    public double getTemperature() {
        return temperature;
    }

    @Override
    public double calculateFactor() {
        double factor = weatherCondition.getBaseFactor();
        
        if (temperature < 20) {
            factor *= 1 + (20 - temperature) * 0.003; 
        } else if (temperature > 25) {
            factor *= 1 + (temperature - 25) * 0.002;
        }

        return factor;
    }
} 