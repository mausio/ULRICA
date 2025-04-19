package service;

import java.util.Random;
import java.util.Scanner;

import model.CarProfile;
import model.route.ChargingStation;
import model.route.ConsoleRouteObserver;
import model.route.RouteManager;

public class RoutePlannerService {
    private final Scanner scanner;
    private final Random random;
    private static final double MIN_STATION_DISTANCE = 20.0;
    private static final double MAX_STATION_DISTANCE = 200.0;
    private static final double[] CHARGING_POWERS = {50.0, 100.0, 150.0, 200.0};

    public RoutePlannerService(Scanner scanner) {
        this.scanner = scanner;
        this.random = new Random();
    }

    public void planRoute(CarProfile profile) {
        System.out.println("\nRoute Planner with Charging Stops");
        System.out.println("--------------------------------");

        double startPoint = getDoubleInput("Enter start point (km): ");
        double endPoint = getDoubleInput("Enter end point (km) [max 2000]: ", 2000);
        double routeLength = endPoint - startPoint;
        double initialBattery = getDoubleInput("Enter initial battery percentage (0-100): ");

        // Use car profile's charging curve to determine optimal target SoC
        double targetBattery = determineOptimalTargetSoC(profile);
        
        // Calculate consumption based on car profile
        double consumption = calculateAverageConsumption(profile);

        System.out.println("\nRoute Parameters:");
        System.out.printf("- Start point: %.1f km\n", startPoint);
        System.out.printf("- End point: %.1f km\n", endPoint);
        System.out.printf("- Total distance: %.1f km\n", routeLength);
        System.out.printf("- Initial battery: %.1f%%\n", initialBattery);
        System.out.printf("- Target charging level: %.1f%%\n", targetBattery);
        System.out.printf("- Energy consumption: %.1f kWh/100km\n", consumption);
        System.out.printf("- Battery capacity: %.1f kWh\n", profile.getBatteryProfile().getCurrentCapacity());
        System.out.printf("- Max charging power: %.1f kW\n", profile.getMaxDcChargingPower());

        RouteManager routeManager = new RouteManager(
            routeLength,
            initialBattery,
            targetBattery,
            consumption,
            profile
        );

        routeManager.addObserver(new ConsoleRouteObserver(startPoint));

        System.out.println("\nGenerating charging stations...");
        generateRandomChargingStations(routeManager, startPoint, endPoint);

        System.out.println("\nStarting route simulation...\n");
        routeManager.startRoute();

        System.out.println("\nPress Enter to continue...");
        scanner.nextLine();
    }

    private void generateRandomChargingStations(RouteManager routeManager, double startPoint, double endPoint) {
        double currentPosition = startPoint;
        int stationCount = 0;
        double maxRange = calculateMaxRange(routeManager);

        while (currentPosition < endPoint) {
            // Generate random distance to next station within bounds
            // But ensure it's not further than the car's max range minus safety buffer
            double maxDistance = Math.min(MAX_STATION_DISTANCE, maxRange - 20.0);
            if (maxDistance < MIN_STATION_DISTANCE) {
                maxDistance = MIN_STATION_DISTANCE;
            }
            
            double distance = MIN_STATION_DISTANCE + random.nextDouble() * (maxDistance - MIN_STATION_DISTANCE);
            currentPosition += distance;

            if (currentPosition >= endPoint) {
                break;
            }

            // Select random charging power
            double chargingPower = CHARGING_POWERS[random.nextInt(CHARGING_POWERS.length)];

            ChargingStation station = new ChargingStation(
                stationCount + 1,
                currentPosition,
                chargingPower
            );
            routeManager.addChargingStation(station);
            stationCount++;
            System.out.printf("Added charging station at %.1f km (%.0f kW)\n", currentPosition, chargingPower);
        }
        System.out.printf("Total charging stations added: %d\n", stationCount);
    }

    private double calculateMaxRange(RouteManager routeManager) {
        double batteryCapacity = routeManager.getCarProfile().getBatteryProfile().getCurrentCapacity();
        double consumption = routeManager.getConsumptionPerKm();
        return (batteryCapacity / consumption) * 0.8; // 80% of max range for safety
    }

    private double determineOptimalTargetSoC(CarProfile profile) {
        // Find the SoC where charging power drops significantly (below 20% of max power)
        double maxPower = profile.getMaxDcChargingPower();
        double optimalSoC = 80.0; // Default to 80% if no charging profile

        if (profile.getChargingProfile() != null) {
            for (var point : profile.getChargingProfile().getChargingCurve()) {
                if (point.getChargingPower() < maxPower * 0.2) {
                    optimalSoC = point.getBatteryPercentage();
                    break;
                }
            }
        }

        return optimalSoC;
    }

    private double calculateAverageConsumption(CarProfile profile) {
        if (profile.getConsumptionProfile() != null) {
            return profile.getConsumptionProfile().getConsumptionAtSpeed(100); // Use 100 km/h as reference
        }
        // Fallback: estimate based on WLTP range and battery capacity
        return (profile.getBatteryProfile().getCurrentCapacity() * 100.0) / profile.getWltpRange();
    }

    private double getDoubleInput(String prompt) {
        return getDoubleInput(prompt, Double.POSITIVE_INFINITY);
    }

    private double getDoubleInput(String prompt, double maxValue) {
        while (true) {
            System.out.print(prompt);
            try {
                double value = Double.parseDouble(scanner.nextLine());
                if (value < 0) {
                    System.out.println("Please enter a positive number.");
                    continue;
                }
                if (value > maxValue) {
                    System.out.printf("Please enter a number less than or equal to %.1f.\n", maxValue);
                    continue;
                }
                if (prompt.contains("percentage") && value > 100) {
                    System.out.println("Please enter a percentage between 0 and 100.");
                    continue;
                }
                return value;
            } catch (NumberFormatException e) {
                System.out.println("Please enter a valid number.");
            }
        }
    }
} 