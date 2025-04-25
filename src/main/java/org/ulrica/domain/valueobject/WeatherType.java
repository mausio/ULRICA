package org.ulrica.domain.valueobject;

public enum WeatherType {
    SUNNY("Clear skies with direct sunlight"),
    CLOUDY("Overcast conditions with limited sunlight"),
    RAIN("Rainy conditions with wet roads"),
    SNOW("Snowy conditions with reduced traction"),
    STRONG_WIND("Windy conditions with increased air resistance");

    private final String description;

    WeatherType(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
} 