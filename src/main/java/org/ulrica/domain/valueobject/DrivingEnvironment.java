package org.ulrica.domain.valueobject;

public enum DrivingEnvironment {
    CITY("Urban driving with frequent stops", 40.0),
    RURAL("Rural roads with moderate speeds", 80.0),
    HIGHWAY("Highway driving at consistent speeds", 110.0);

    private final String description;
    private final double avgSpeedKmh;

    DrivingEnvironment(String description, double avgSpeedKmh) {
        this.description = description;
        this.avgSpeedKmh = avgSpeedKmh;
    }

    public String getDescription() {
        return description;
    }

    public double getAvgSpeedKmh() {
        return avgSpeedKmh;
    }
} 