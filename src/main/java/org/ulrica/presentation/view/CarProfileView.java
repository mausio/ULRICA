package org.ulrica.presentation.view;

import java.util.List;

import org.ulrica.domain.entity.CarProfile;
import org.ulrica.domain.valueobject.BatteryType;

public class CarProfileView {
    public void showMenu() {
        System.out.println("\nCar Profile Management");
        System.out.println("1. View all car profiles");
        System.out.println("2. Create new car profile");
        System.out.println("3. Delete car profile");
        System.out.println("4. Edit car profile");
        System.out.println("5. Back to main menu");
        System.out.print("\nEnter your choice (1-5): ");
    }

    public void showAllCarProfiles(List<CarProfile> profiles) {
        System.out.println("\nCar Profiles:");
        for (CarProfile profile : profiles) {
            System.out.println("----------------------------------------");
            System.out.println("Name: " + profile.getName());
            System.out.println("Manufacturer: " + profile.getManufacturer());
            System.out.println("Model: " + profile.getModel());
            System.out.println("Build Year: " + profile.getYear());
            System.out.println("Heat Pump: " + (profile.hasHeatPump() ? "Yes" : "No"));
            System.out.println("----------------------------------------");
        }
    }

    public void askToSelectProfile() {
        System.out.print("\nWould you like to select a profile? (yes/no): ");
    }

    public void showProfileSelection(List<CarProfile> profiles) {
        System.out.println("\nSelect a profile:");
        for (int i = 0; i < profiles.size(); i++) {
            System.out.println((i + 1) + ". " + profiles.get(i).getName());
        }
        System.out.print("\nEnter number (1-" + profiles.size() + "): ");
    }

    public void showSelectedProfile(CarProfile profile) {
        System.out.println("Selected profile: " + profile.getName());
    }

    public void showCreateProfileHeader() {
        System.out.println("\nCreate New Car Profile");
    }

    public void showBatteryTypes() {
        System.out.println("\nBattery Profile Setup:");
        System.out.println("Available battery types:");
        int i = 1;
        for (BatteryType type : BatteryType.values()) {
            System.out.println(i++ + ". " + type.name() + " (" + type.getDescription() + ")");
        }
    }

    public void showProfileCreated(CarProfile profile) {
        System.out.println("\nCar profile created and selected successfully!");
        System.out.println("\nPress Enter to continue...");
    }

    public void showProfileDeleted(CarProfile profile) {
        System.out.println("\nProfile deleted: " + profile.getName());
    }

    public void showEditNotImplemented() {
        System.out.println("\nEdit functionality not yet implemented.");
    }

    public void showInvalidChoice() {
        System.out.println("\nInvalid choice. Please try again.");
    }

    public void showPrompt(String prompt) {
        System.out.print(prompt);
    }
} 