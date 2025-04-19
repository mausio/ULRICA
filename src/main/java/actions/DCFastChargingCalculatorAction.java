package actions;

import java.util.Scanner;

import interfaces.ProfileAction;
import model.CarProfile;
import model.charging.ChargingProfile;
import service.ChargingCalculatorService;

public class DCFastChargingCalculatorAction implements ProfileAction {
    private final Scanner scanner = new Scanner(System.in);
    private final ChargingCalculatorService calculatorService = new ChargingCalculatorService();

    @Override
    public void execute(CarProfile profile) {
        System.out.println("\n=== DC (Fast) Charging Calculator ===");
        
        if (profile.getChargingProfile() == null) {
            System.out.println("\nError: This car profile does not have a charging profile defined!");
            waitForEnter();
            return;
        }
        
        ChargingProfile chargingProfile = createChargingProfile(profile);
        
        System.out.print("Enter starting State of Charge (%) [0-100]: ");
        double startSoc = getValidPercentage();
        
        System.out.print("Enter target State of Charge (%) [0-100]: ");
        double targetSoc = getValidPercentage();
        
        System.out.print("Enter maximum charging station power (kW): ");
        double maxPower = getValidPower();

        System.out.print("Enter ambient temperature (°C): ");
        double temperature = getValidTemperature();

        double chargingTime = calculatorService.calculateChargingTime(chargingProfile, startSoc, targetSoc, maxPower, temperature);
        String formattedTime = calculatorService.formatChargingTime(chargingTime);
        double energyToAdd = calculatorService.calculateEnergyToBeAdded(chargingProfile, startSoc, targetSoc);

        System.out.println("\n=== Charging Time Estimate ===");
        System.out.println("Charging Time: " + formattedTime);
        System.out.println("Battery Capacity: " + chargingProfile.getBatteryCapacity() + " kWh");
        System.out.println("Energy to be added: " + String.format("%.1f", energyToAdd) + " kWh");
        System.out.println("Battery Chemistry: " + chargingProfile.getBatteryChemistry());
        
        double finalTemp = Math.min(temperature + (chargingTime / 60.0) * 10.0, 35.0);
        System.out.println("Temperature: " + String.format("%.1f°C → %.1f°C", temperature, finalTemp));
        System.out.println("Power Reduction: " + "30% (for battery longevity)");

        String recommendation = calculatorService.getChargingRecommendation(
            startSoc, targetSoc, temperature, chargingProfile.getBatteryChemistry());
        if (!recommendation.isEmpty()) {
            System.out.println("\nNote: " + recommendation);
        }

        waitForEnter();
    }

    private ChargingProfile createChargingProfile(CarProfile profile) {
        double batteryCapacity = profile.getBatteryProfile().getInitialCapacity();
        ChargingProfile chargingProfile = new ChargingProfile(
            profile.getName(), 
            batteryCapacity,
            profile.getBatteryProfile().getBatteryType()
        );
        
        for (model.ChargingProfile.ChargingCurvePoint point : profile.getChargingProfile().getChargingCurve()) {
            chargingProfile.addChargingPoint((int) point.getBatteryPercentage(), point.getChargingPower());
        }
        
        return chargingProfile;
    }

    private double getValidPercentage() {
        while (true) {
            try {
                double value = Double.parseDouble(scanner.nextLine().trim());
                if (value >= 0 && value <= 100) {
                    return value;
                }
            } catch (NumberFormatException ignored) {
            }
            System.out.print("Please enter a valid percentage (0-100): ");
        }
    }

    private double getValidPower() {
        while (true) {
            try {
                double value = Double.parseDouble(scanner.nextLine().trim());
                if (value > 0) {
                    return value;
                }
            } catch (NumberFormatException ignored) {
            }
            System.out.print("Please enter a valid power value (greater than 0): ");
        }
    }

    private double getValidTemperature() {
        while (true) {
            try {
                return Double.parseDouble(scanner.nextLine().trim());
            } catch (NumberFormatException ignored) {
            }
            System.out.print("Please enter a valid temperature in Celsius: ");
        }
    }

    private void waitForEnter() {
        System.out.println("\nPress Enter to continue...");
        scanner.nextLine();
    }

    @Override
    public String getDisplayName() {
        return "DC (Fast) Charging Calculator";
    }
} 