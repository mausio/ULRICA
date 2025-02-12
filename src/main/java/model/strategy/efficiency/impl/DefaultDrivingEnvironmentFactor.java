package model.strategy.efficiency.impl;

import model.strategy.efficiency.DrivingEnvironmentFactor;

public class DefaultDrivingEnvironmentFactor implements DrivingEnvironmentFactor {
    private final DrivingEnvironment environment;
    private final double averageSpeed;

    public DefaultDrivingEnvironmentFactor(DrivingEnvironment environment, double averageSpeed) {
        this.environment = environment;
        this.averageSpeed = averageSpeed;
    }

    @Override
    public DrivingEnvironment getDrivingEnvironment() {
        return environment;
    }

    @Override
    public double getAverageSpeed() {
        return averageSpeed;
    }

    @Override
    public double calculateFactor() {
        double factor = environment.getBaseFactor();
        
        switch (environment) {
            case CITY:

                if (averageSpeed < 30) {
                    factor *= 1.1; 
                } else if (averageSpeed > 50) {
                    factor *= 1 + (averageSpeed - 50) * 0.002; 
                }
                break;
            case RURAL:
                if (averageSpeed < 70) {
                    factor *= 1 + (70 - averageSpeed) * 0.001;
                } else if (averageSpeed > 90) {
                    factor *= 1 + (averageSpeed - 90) * 0.002;
                }
                break;
            case HIGHWAY:
                if (averageSpeed > 120) {
                    factor *= 1 + (averageSpeed - 120) * 0.003;
                }
                break;
        }
        
        return factor;
    }
} 