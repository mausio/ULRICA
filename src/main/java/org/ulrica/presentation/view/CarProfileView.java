package org.ulrica.presentation.view;

import java.util.List;

import org.ulrica.application.port.out.CarProfileMenuOutputPort;
import org.ulrica.domain.entity.CarProfile;
import org.ulrica.domain.valueobject.BatteryType;
import org.ulrica.presentation.util.AnsiColors;

public class CarProfileView implements CarProfileMenuOutputPort {
    private static final String SEPARATOR = "─".repeat(80);
    private static final String SECTION_SEPARATOR = "═".repeat(80);

    @Override
    public void showCarProfileMenu() {
        System.out.println("\n=== Car Profile Management ===");
        System.out.println("1. View all car profiles");
        System.out.println("2. Create new car profile");
        System.out.println("3. Delete car profile");
        System.out.println("4. Edit car profile");
        System.out.println("5. Back to main menu");
        System.out.print("\nEnter your choice (1-5): ");
    }
    
    @Override
    public void showMenuOptions() {
        System.out.println("1. View all car profiles");
        System.out.println("2. Create new car profile");
        System.out.println("3. Delete car profile");
        System.out.println("4. Edit car profile");
        System.out.println("5. Back to main menu");
    }
    
    @Override
    public void showPrompt() {
        System.out.print("\nEnter your choice (1-5): ");
    }

    @Override
    public void showNoProfilesMessage() {
        System.out.println(AnsiColors.YELLOW + "No car profiles found!" + AnsiColors.RESET);
    }

    @Override
    public void showAllCarProfiles(List<CarProfile> profiles) {
        for (CarProfile profile : profiles) {
            System.out.println("\n────────────────────────────────────────────────────────────────────────────────");
            System.out.println("Name: " + profile.getName());
            System.out.println("Manufacturer: " + profile.getManufacturer());
            System.out.println("Model: " + profile.getModel());
            System.out.println("Build Year: " + profile.getYear());
            System.out.println("Heat Pump: " + (profile.hasHeatPump() ? "Yes" : "No"));
            System.out.println("────────────────────────────────────────────────────────────────────────────────");
        }
    }

    @Override
    public void askToSelectProfile() {
        System.out.print("\nWould you like to select a profile? (yes/no): ");
    }

    @Override
    public void showProfileSelection(List<CarProfile> profiles) {
        System.out.println("\nSelect a profile:");
        for (int i = 0; i < profiles.size(); i++) {
            System.out.println((i + 1) + ". " + profiles.get(i).getName());
        }
        System.out.print("\nEnter number (1-" + profiles.size() + "): ");
    }

    @Override
    public void showSelectedProfile(CarProfile profile) {
        System.out.println("\nSelected profile: " + profile.getName());
        System.out.println("Manufacturer: " + profile.getManufacturer());
        System.out.println("Model: " + profile.getModel());
        System.out.println("Build Year: " + profile.getYear());
        System.out.println("Heat Pump: " + (profile.hasHeatPump() ? "Yes" : "No"));
    }

    @Override
    public void showCreateProfileHeader() {
        System.out.println("\n=== Create New Car Profile ===" + AnsiColors.RESET);
    }

    @Override
    public void showBatteryTypes() {
        System.out.println("\n" + AnsiColors.BLUE_BOLD + "Battery Profile Setup:" + AnsiColors.RESET);
        System.out.println("Available battery types:");
        int i = 1;
        for (BatteryType type : BatteryType.values()) {
            System.out.println(i++ + ". " + type.name() + " (" + type.getDescription() + ")");
        }
    }

    @Override
    public void showConsumptionProfileSetup() {
        System.out.println("\n" + AnsiColors.BLUE_BOLD + "Consumption Profile Setup:" + AnsiColors.RESET);
        System.out.println("Please enter the consumption values at different speeds:");
    }

    @Override
    public void showProfileCreated(CarProfile profile) {
        System.out.println("\n" + SECTION_SEPARATOR);
        System.out.println(AnsiColors.BLUE_BOLD + "Car Profile Created Successfully!" + AnsiColors.RESET);
        System.out.println(SECTION_SEPARATOR);
        
        System.out.println("\n" + AnsiColors.YELLOW_BOLD + "Basic Information" + AnsiColors.RESET);
        System.out.println(SEPARATOR);
        System.out.println(AnsiColors.GREEN + "Name: " + AnsiColors.RESET + profile.getName());
        System.out.println(AnsiColors.GREEN + "Manufacturer: " + AnsiColors.RESET + profile.getManufacturer());
        System.out.println(AnsiColors.GREEN + "Model: " + AnsiColors.RESET + profile.getModel());
        System.out.println(AnsiColors.GREEN + "Year: " + AnsiColors.RESET + profile.getYear());
        System.out.println(AnsiColors.GREEN + "Heat Pump: " + AnsiColors.RESET + (profile.hasHeatPump() ? "Yes" : "No"));
        System.out.println(AnsiColors.GREEN + "WLTP Range: " + AnsiColors.RESET + String.format("%.1f km", profile.getWltpRangeKm()));
        System.out.println(AnsiColors.GREEN + "Max DC Power: " + AnsiColors.RESET + String.format("%.1f kW", profile.getMaxDcPowerKw()));
        System.out.println(AnsiColors.GREEN + "Max AC Power: " + AnsiColors.RESET + String.format("%.1f kW", profile.getMaxAcPowerKw()));
        
        System.out.println("\n" + AnsiColors.YELLOW_BOLD + "Battery Information" + AnsiColors.RESET);
        System.out.println(SEPARATOR);
        System.out.println(AnsiColors.GREEN + "Type: " + AnsiColors.RESET + profile.getBatteryProfile().getType().name());
        System.out.println(AnsiColors.GREEN + "Capacity: " + AnsiColors.RESET + String.format("%.1f kWh", profile.getBatteryProfile().getCapacityKwh()));
        System.out.println(AnsiColors.GREEN + "Degradation: " + AnsiColors.RESET + String.format("%.1f%%", profile.getBatteryProfile().getDegradationPercent()));
        System.out.println(AnsiColors.GREEN + "Remaining Capacity: " + AnsiColors.RESET + String.format("%.1f kWh", profile.getBatteryProfile().getRemainingCapacityKwh()));
        
        System.out.println("\n" + AnsiColors.YELLOW_BOLD + "Consumption Profile" + AnsiColors.RESET);
        System.out.println(SEPARATOR);
        System.out.println(AnsiColors.GREEN + "At 50 km/h: " + AnsiColors.RESET + String.format("%.1f kWh/100km", profile.getConsumptionProfile().getConsumptionAt50Kmh()));
        System.out.println(AnsiColors.GREEN + "At 100 km/h: " + AnsiColors.RESET + String.format("%.1f kWh/100km", profile.getConsumptionProfile().getConsumptionAt100Kmh()));
        System.out.println(AnsiColors.GREEN + "At 130 km/h: " + AnsiColors.RESET + String.format("%.1f kWh/100km", profile.getConsumptionProfile().getConsumptionAt130Kmh()));
        System.out.println(AnsiColors.GREEN + "Average Consumption: " + AnsiColors.RESET + String.format("%.1f kWh/100km", profile.getConsumptionProfile().getAverageConsumption()));
        
        System.out.println("\nPress Enter to continue...");
    }

    @Override
    public void showProfileDeleted(CarProfile profile) {
        System.out.println("\n" + AnsiColors.RED + "Profile deleted: " + AnsiColors.RESET + profile.getName());
    }

    @Override
    public void showInvalidChoice() {
        System.out.println(AnsiColors.RED + "Invalid choice. Please try again." + AnsiColors.RESET);
    }

    @Override
    public void showPrompt(String prompt) {
        System.out.print(prompt);
    }
    
    @Override
    public void showEditNotImplemented() {
        System.out.println(AnsiColors.YELLOW + "Edit functionality is not yet implemented." + AnsiColors.RESET);
    }
    
    public void showMenu() {
        showCarProfileMenu();
    }
} 