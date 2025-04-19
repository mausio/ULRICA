package model.route;

import java.util.ArrayList;
import java.util.List;

import model.CarProfile;
import model.ChargingProfile.ChargingCurvePoint;
import model.route.charging.ChargingStrategy;
import model.route.charging.MinimalChargingStrategy;

public class RouteManager {
    private final List<RouteObserver> observers;
    private final List<ChargingStation> chargingStations;
    private final double routeLength;
    private final double targetSoC;
    private CarProfile carProfile;
    private double currentPosition;
    private double currentBatteryPercentage;
    private double consumptionPerKm;
    private RouteStatus.RouteState currentState;
    private ChargingStrategy chargingStrategy;
    private static final double AVERAGE_SPEED = 100.0; // km/h
    private static final double SAFETY_BUFFER = 20.0; // km

    public RouteManager(double routeLength, double initialBatteryPercentage, double targetSoC, double consumptionPer100km, CarProfile carProfile) {
        this.observers = new ArrayList<>();
        this.chargingStations = new ArrayList<>();
        this.routeLength = routeLength;
        this.currentBatteryPercentage = initialBatteryPercentage;
        this.targetSoC = targetSoC;
        this.consumptionPerKm = consumptionPer100km / 100.0; // Convert from kWh/100km to kWh/km
        this.currentPosition = 0;
        this.currentState = RouteStatus.RouteState.ACTIVE;
        this.carProfile = carProfile;
        this.chargingStrategy = new MinimalChargingStrategy(); // Default strategy
    }

    public RouteManager(double routeLength, double initialBatteryPercentage, double targetSoC, double consumptionPerKm) {
        this.observers = new ArrayList<>();
        this.chargingStations = new ArrayList<>();
        this.routeLength = routeLength;
        this.currentBatteryPercentage = initialBatteryPercentage;
        this.targetSoC = targetSoC;
        this.consumptionPerKm = consumptionPerKm;
        this.currentPosition = 0;
        this.currentState = RouteStatus.RouteState.ACTIVE;
        this.carProfile = null;
    }

    public void addChargingStation(ChargingStation station) {
        chargingStations.add(station);
    }

    public void addObserver(RouteObserver observer) {
        observers.add(observer);
    }

    public void removeObserver(RouteObserver observer) {
        observers.remove(observer);
    }

    public void startRoute() {
        if (!validateRouteFeasibility()) {
            return;
        }

        while (currentPosition < routeLength && currentState != RouteStatus.RouteState.FAILED) {
            updateRoute();
            ChargingStation nextStation = findNextChargingStation();
            if (needsCharging(nextStation)) {
                if (!canReachStation(nextStation)) {
                    handleUnreachableStation(nextStation);
                    break;
                }
                performCharging(nextStation);
            }
        }

        if (currentState != RouteStatus.RouteState.FAILED) {
            notifyRouteCompleted();
        }
    }

    private boolean validateRouteFeasibility() {
        double maxRange = calculateMaxRange();
        ChargingStation firstStation = findNextChargingStation();

        if (firstStation == null && routeLength > maxRange) {
            String error = String.format(
                "Route not possible - Total distance %.1f km exceeds maximum range %.1f km with no charging stations\n" +
                "Suggestions:\n" +
                "1. Add charging stations along the route\n" +
                "2. Start with a higher battery level (current: %.1f%%)\n" +
                "3. Reduce energy consumption by:\n" +
                "   - Lowering speed\n" +
                "   - Using ECO mode\n" +
                "   - Optimizing climate control",
                routeLength, maxRange, currentBatteryPercentage);
            notifyRouteFailed(error);
            return false;
        }

        if (firstStation != null && firstStation.getPosition() > maxRange) {
            String error = String.format(
                "Route not possible - First charging station at %.1f km exceeds maximum range %.1f km\n" +
                "Suggestions:\n" +
                "1. Add a charging station before %.1f km\n" +
                "2. Start with a higher battery level (current: %.1f%%)\n" +
                "3. Reduce energy consumption by:\n" +
                "   - Lowering speed\n" +
                "   - Using ECO mode\n" +
                "   - Optimizing climate control",
                firstStation.getPosition(), maxRange, maxRange * 0.8, currentBatteryPercentage);
            notifyRouteFailed(error);
            return false;
        }

        return true;
    }

    private double calculateMaxRange() {
        if (carProfile != null) {
            double batteryCapacity = carProfile.getBatteryProfile().getCurrentCapacity();
            double availableEnergy = batteryCapacity * currentBatteryPercentage / 100.0;
            return (availableEnergy / consumptionPerKm) * 100.0; // Convert to actual range
        }
        return 0.0;
    }

    private double calculateDrivingTime(double distance) {
        return distance / AVERAGE_SPEED * 60.0; // Convert to minutes
    }

    private boolean canReachStation(ChargingStation station) {
        if (station == null) {
            return true;
        }
        double distanceToStation = station.getPosition() - currentPosition;
        double currentRange = calculateRemainingRange();
        return currentRange >= distanceToStation;
    }

    private void handleUnreachableStation(ChargingStation station) {
        double currentRange = calculateRemainingRange();
        double distanceToStation = station.getPosition() - currentPosition;
        double additionalRangeNeeded = distanceToStation - currentRange;
        
        String error = String.format(
            "Battery drain too high! Cannot reach next charging station at %.1f km (current range: %.1f km)\n" +
            "Suggestions:\n" +
            "1. Need %.1f km more range\n" +
            "2. Consider:\n" +
            "   - Reducing speed\n" +
            "   - Using ECO mode\n" +
            "   - Adding an intermediate charging station\n" +
            "   - Optimizing climate control settings",
            station.getPosition(),
            currentRange,
            additionalRangeNeeded);
        notifyRouteFailed(error);
    }

    private void updateRoute() {
        double step = Math.min(1.0, routeLength - currentPosition);
        currentPosition += step;
        
        // Calculate energy consumption for this step
        double energyConsumed = (consumptionPerKm * step); // Already in kWh/km
        double batteryCapacity = carProfile.getBatteryProfile().getCurrentCapacity();
        double percentageConsumed = (energyConsumed / batteryCapacity) * 100.0;
        currentBatteryPercentage -= percentageConsumed;
        
        double drivingTime = calculateDrivingTime(step);
        notifyRouteUpdate(drivingTime);
    }

    private ChargingStation findNextChargingStation() {
        return chargingStations.stream()
                .filter(station -> station.getPosition() > currentPosition)
                .filter(ChargingStation::isAvailable)
                .findFirst()
                .orElse(null);
    }

    private boolean needsCharging(ChargingStation nextStation) {
        if (nextStation == null) {
            return false;
        }
        double distanceToStation = nextStation.getPosition() - currentPosition;
        double rangeNeeded = distanceToStation + SAFETY_BUFFER;
        return calculateRemainingRange() < rangeNeeded;
    }

    private void performCharging(ChargingStation station) {
        currentState = RouteStatus.RouteState.CHARGING;
        double startSoC = currentBatteryPercentage;
        double calculatedTargetSoC = chargingStrategy.calculateTargetSoC(
            startSoC,
            routeLength - currentPosition,
            routeLength,
            station
        );
        double chargingPower = calculateChargingPower(station, startSoC);
        double estimatedDuration = calculateChargingDuration(startSoC, calculatedTargetSoC, station);

        ChargingSession session = new ChargingSession(
            station.getId(),
            station.getPosition(),
            startSoC,
            calculatedTargetSoC,
            chargingPower,
            estimatedDuration
        );

        notifyChargingStarted(session);
        currentBatteryPercentage = calculatedTargetSoC;
        currentState = RouteStatus.RouteState.ACTIVE;
        notifyChargingCompleted(session);
    }

    private double calculateChargingPower(ChargingStation station, double currentSoC) {
        if (carProfile == null || carProfile.getChargingProfile() == null) {
            return Math.min(station.getMaxPower(), 150.0);
        }

        // Find the charging power from the car's charging curve
        double maxCarPower = carProfile.getMaxDcChargingPower();
        double stationPower = station.getMaxPower();
        
        // Find the applicable charging power from the curve
        for (ChargingCurvePoint point : carProfile.getChargingProfile().getChargingCurve()) {
            if (point.getBatteryPercentage() >= currentSoC) {
                return Math.min(point.getChargingPower(), Math.min(maxCarPower, stationPower));
            }
        }

        return Math.min(maxCarPower, stationPower);
    }

    private double calculateChargingDuration(double startSoC, double targetSoC, ChargingStation station) {
        if (carProfile == null || carProfile.getChargingProfile() == null) {
            return ((targetSoC - startSoC) / 100.0) * 60.0;
        }

        double totalDuration = 0;
        double currentSoC = startSoC;
        double batteryCapacity = carProfile.getBatteryProfile().getCurrentCapacity();
        
        // Calculate charging time in small steps to account for charging curve
        while (currentSoC < targetSoC) {
            double power = calculateChargingPower(station, currentSoC);
            double energyStep = batteryCapacity * 0.01; // 1% of battery capacity
            double stepDuration = (energyStep / power) * 60.0; // Duration in minutes
            
            totalDuration += stepDuration;
            currentSoC += 1.0; // Increment by 1%
        }

        return totalDuration;
    }

    private double calculateRemainingRange() {
        double batteryCapacity = carProfile.getBatteryProfile().getCurrentCapacity();
        double availableEnergy = batteryCapacity * currentBatteryPercentage / 100.0;
        return (availableEnergy / consumptionPerKm); // Already in kWh/km
    }

    private void notifyRouteUpdate(double drivingTime) {
        RouteStatus status = new RouteStatus(
            currentPosition,
            routeLength - currentPosition,
            currentBatteryPercentage,
            drivingTime,
            currentState,
            null
        );
        observers.forEach(observer -> observer.onRouteUpdate(status));
    }

    private void notifyChargingStarted(ChargingSession session) {
        observers.forEach(observer -> observer.onChargingStarted(session));
    }

    private void notifyChargingCompleted(ChargingSession session) {
        observers.forEach(observer -> observer.onChargingCompleted(session));
    }

    private void notifyRouteCompleted() {
        currentState = RouteStatus.RouteState.COMPLETED;
        RouteStatus finalStatus = new RouteStatus(
            currentPosition,
            0,
            currentBatteryPercentage,
            0.0,
            currentState,
            null
        );
        observers.forEach(observer -> observer.onRouteCompleted(finalStatus));
    }

    private void notifyRouteFailed(String errorMessage) {
        currentState = RouteStatus.RouteState.FAILED;
        RouteStatus failedStatus = new RouteStatus(
            currentPosition,
            routeLength - currentPosition,
            currentBatteryPercentage,
            0.0,
            currentState,
            errorMessage
        );
        observers.forEach(observer -> observer.onRouteCompleted(failedStatus));
    }

    public CarProfile getCarProfile() {
        return carProfile;
    }

    public double getConsumptionPerKm() {
        return consumptionPerKm;
    }

    public void setChargingStrategy(ChargingStrategy strategy) {
        this.chargingStrategy = strategy;
    }
} 