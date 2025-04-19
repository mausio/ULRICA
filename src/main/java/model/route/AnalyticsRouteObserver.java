package model.route;

import java.util.ArrayList;
import java.util.List;

public class AnalyticsRouteObserver implements RouteObserver {
    private final List<ChargingSession> chargingSessions;
    private double totalDrivingTime;
    private double totalChargingTime;
    private double totalDistance;
    private double initialBatteryPercentage;
    private double finalBatteryPercentage;
    private double energyConsumed;
    private double energyCharged;

    public AnalyticsRouteObserver() {
        this.chargingSessions = new ArrayList<>();
        this.totalDrivingTime = 0;
        this.totalChargingTime = 0;
        this.totalDistance = 0;
        this.energyConsumed = 0;
        this.energyCharged = 0;
    }

    @Override
    public void onRouteUpdate(RouteStatus status) {
        if (status.state() == RouteStatus.RouteState.ACTIVE) {
            totalDrivingTime += status.drivingTime();
            totalDistance = status.currentPosition();
            
            if (initialBatteryPercentage == 0) {
                initialBatteryPercentage = status.currentBatteryPercentage();
            }
            finalBatteryPercentage = status.currentBatteryPercentage();
        }
    }

    @Override
    public void onChargingStarted(ChargingSession session) {
        chargingSessions.add(session);
    }

    @Override
    public void onChargingCompleted(ChargingSession session) {
        totalChargingTime += session.estimatedDuration();
        double energyAdded = (session.targetSoC() - session.startSoC()) / 100.0 * session.chargingPower();
        energyCharged += energyAdded;
    }

    @Override
    public void onRouteCompleted(RouteStatus finalStatus) {
        finalBatteryPercentage = finalStatus.currentBatteryPercentage();
    }

    public double getAverageSpeed() {
        return totalDistance / (totalDrivingTime / 60.0);
    }

    public double getAverageChargingTime() {
        return chargingSessions.isEmpty() ? 0 : totalChargingTime / chargingSessions.size();
    }

    public double getTotalTripTime() {
        return totalDrivingTime + totalChargingTime;
    }

    public double getChargingTimePercentage() {
        double totalTime = getTotalTripTime();
        return totalTime == 0 ? 0 : (totalChargingTime / totalTime) * 100;
    }

    public double getEnergyEfficiency() {
        return totalDistance == 0 ? 0 : energyConsumed / totalDistance * 100;
    }

    public int getNumberOfChargingStops() {
        return chargingSessions.size();
    }

    public double getAverageDistanceBetweenCharging() {
        return chargingSessions.isEmpty() ? totalDistance : totalDistance / chargingSessions.size();
    }

    public String generateAnalyticsSummary() {
        return String.format("""
            Route Analytics Summary:
            Total Distance: %.1f km
            Total Time: %.1f minutes (%.1f%% charging)
            Average Speed: %.1f km/h
            Number of Charging Stops: %d
            Average Distance Between Charging: %.1f km
            Average Charging Time: %.1f minutes
            Energy Efficiency: %.2f kWh/100km
            Battery: %.1f%% -> %.1f%%""",
            totalDistance,
            getTotalTripTime(),
            getChargingTimePercentage(),
            getAverageSpeed(),
            getNumberOfChargingStops(),
            getAverageDistanceBetweenCharging(),
            getAverageChargingTime(),
            getEnergyEfficiency(),
            initialBatteryPercentage,
            finalBatteryPercentage
        );
    }
} 