package service;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import model.BatteryProfile;
import model.CarProfile;
import model.ChargingProfile;
import model.ConsumptionProfile;
import model.strategy.ConsumptionBasedRangeStrategy;
import repository.CarProfileRepository;
import utils.generalUtils.ConsoleInteractorUtil;
import utils.generalUtils.InputCleanerUtil;
import utils.validation.CarProfileValidator;
import utils.validation.CarProfileValidator.ValidationResult;

public class CarProfileService {
    private final CarProfileRepository repository;
    private final Scanner scanner;
    private CarProfile selectedProfile;

    public CarProfileService(CarProfileRepository repository) {
        this.repository = repository;
        this.scanner = new Scanner(System.in);
    }

    public void showMenu() {
        while (true) {
            ConsoleInteractorUtil.clear();
            System.out.println("\nCar Profile Management");
            if (selectedProfile != null) {
                System.out.println("Currently selected profile: " + selectedProfile.getName());
            }
            System.out.println("1. View all car profiles");
            System.out.println("2. Create new car profile");
            System.out.println("3. Delete car profile");
            System.out.println("4. Edit car profile");
            System.out.println("5. Exit");
            System.out.print("\nEnter your choice (1-5): ");

            String choice = scanner.nextLine();
            switch (choice) {
                case "1":
                    viewAllProfiles();
                    break;
                case "2":
                    createNewProfile();
                    break;
                case "3":
                    deleteProfile();
                    break;
                case "4":
                    editProfile();
                    break;
                case "5":
                    return;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    private void viewAllProfiles() {
        List<CarProfile> profiles = repository.findAll();
        if (profiles.isEmpty()) {
            System.out.println("\nNo car profiles found.");
            waitForEnter();
            return;
        }

        System.out.println("\nCar Profiles:");
        for (CarProfile profile : profiles) {
            displayProfile(profile);
        }
        
        System.out.print("\nWould you like to select a profile? (yes/no): ");
        if (InputCleanerUtil.formatYesOrNoToBoolean(scanner.nextLine())) {
            selectProfile(profiles);
        } else {
            waitForEnter();
        }
    }

    private void selectProfile(List<CarProfile> profiles) {
        System.out.println("\nSelect a profile:");
        for (int i = 0; i < profiles.size(); i++) {
            System.out.println((i + 1) + ". " + profiles.get(i).getName());
        }

        System.out.print("\nEnter number (1-" + profiles.size() + "): ");
        try {
            int choice = Integer.parseInt(scanner.nextLine()) - 1;
            if (choice >= 0 && choice < profiles.size()) {
                selectedProfile = profiles.get(choice);
                System.out.println("Selected profile: " + selectedProfile.getName());
            } else {
                System.out.println("Invalid choice.");
            }
        } catch (NumberFormatException e) {
            System.out.println("Invalid input. Please enter a number.");
        }
        waitForEnter();
    }

    private void createNewProfile() {
        System.out.println("\nCreate New Car Profile");
        
        // Basic information with validation
        String name = getValidatedInput("Enter profile name: ", "Profile name", this::validateName);
        String manufacturer = getValidatedInput("Enter manufacturer: ", "Manufacturer", this::validateName);
        String model = getValidatedInput("Enter model: ", "Model", this::validateName);
        int buildYear = getValidatedIntInput("Enter build year: ", this::validateBuildYear);
        boolean hasHeatPump = InputCleanerUtil.formatYesOrNoToBoolean(
            getValidatedInput("Has heat pump? (yes/no): ", "Heat pump", this::validateYesNo));

        // Technical specifications with validation
        double wltpRange = getValidatedDoubleInput("Enter WLTP range (km): ", this::validateWltpRange);
        double realRange = wltpRange * 0.9; // Convert WLTP to real range (90%)
        double maxDcChargingPower = getValidatedDoubleInput("Enter maximum DC charging power (kW): ", this::validateChargingPower);
        double maxAcChargingPower = getValidatedDoubleInput("Enter maximum AC charging power (kW): ", this::validateChargingPower);

        // Create the basic profile (with temporary 0 battery capacity - will be set in battery profile)
        CarProfile newProfile = new CarProfile(name, manufacturer, model, buildYear, hasHeatPump,
                0.0, realRange, maxDcChargingPower, maxAcChargingPower);

        // Create mandatory battery profile
        newProfile.setBatteryProfile(createBatteryProfile());

        // Create consumption profile
        System.out.println("\nCreating consumption profile:");
        ConsumptionProfile consumptionProfile = createConsumptionProfile();
        newProfile.setConsumptionProfile(consumptionProfile);
        
        // Display calculated ranges and consumptions for different modes
        System.out.println("\nCalculated ranges and consumptions for mixed driving style (city/rural/highway):");
        double normalConsumption = consumptionProfile.getAverageConsumption();
        
        // Using WLTP-based simple factor strategy (default)
        System.out.println("\nWLTP-based range calculation:");
        System.out.printf("ECO Mode: %.1f km (%.1f kWh/100km)\n", 
            newProfile.calculateRange(CarProfile.EfficiencyMode.ECO),
            normalConsumption * CarProfile.EfficiencyMode.ECO.getConsumptionFactor());
        System.out.printf("Normal Mode: %.1f km (%.1f kWh/100km)\n", 
            newProfile.calculateRange(CarProfile.EfficiencyMode.NORMAL),
            normalConsumption);
        System.out.printf("Sport Mode: %.1f km (%.1f kWh/100km)\n", 
            newProfile.calculateRange(CarProfile.EfficiencyMode.SPORT),
            normalConsumption * CarProfile.EfficiencyMode.SPORT.getConsumptionFactor());
            
        // Switch to consumption-based strategy for more accurate calculation
        newProfile.setRangeCalculationStrategy(new ConsumptionBasedRangeStrategy());
        System.out.println("\nConsumption-based range calculation:");
        System.out.printf("ECO Mode: %.1f km (%.1f kWh/100km)\n", 
            newProfile.calculateRange(CarProfile.EfficiencyMode.ECO),
            normalConsumption * CarProfile.EfficiencyMode.ECO.getConsumptionFactor());
        System.out.printf("Normal Mode: %.1f km (%.1f kWh/100km)\n", 
            newProfile.calculateRange(CarProfile.EfficiencyMode.NORMAL),
            normalConsumption);
        System.out.printf("Sport Mode: %.1f km (%.1f kWh/100km)\n", 
            newProfile.calculateRange(CarProfile.EfficiencyMode.SPORT),
            normalConsumption * CarProfile.EfficiencyMode.SPORT.getConsumptionFactor());

        if (wantToAddProfile("Would you like to add a charging profile? (yes/no): ")) {
            newProfile.setChargingProfile(createChargingProfile(maxDcChargingPower, maxAcChargingPower));
            if (newProfile.getChargingProfile() != null) {
                System.out.println("\nCharging Curve Overview:");
                newProfile.getChargingProfile().displayChargingCurve();
            }
        }

        // Save and select the profile
        repository.save(newProfile);
        selectedProfile = newProfile;
        System.out.println("\nCar profile created and selected successfully!");
        waitForEnter();
    }

    private ConsumptionProfile createConsumptionProfile() {
        ConsumptionProfile profile = new ConsumptionProfile();
        
        System.out.println("Enter consumption values for Normal mode (baseline):");
        // Standard speed points
        int[] speeds = {50, 100, 130};
        for (int speed : speeds) {
            double consumption;
            while (true) {
                System.out.printf("Enter consumption at %d km/h (kWh/100km): ", speed);
                try {
                    consumption = Double.parseDouble(scanner.nextLine().trim());
                    ValidationResult result = CarProfileValidator.validateConsumption(consumption);
                    if (result.isValid()) {
                        break;
                    }
                    System.out.println(result.getErrors().get(0));
                } catch (NumberFormatException e) {
                    System.out.println("Please enter a valid number.");
                }
            }
            profile.addConsumptionValue(speed, consumption);
        }
        
        return profile;
    }

    private BatteryProfile createBatteryProfile() {
        System.out.println("\nBattery Profile Setup:");
        System.out.println("Available battery types:");
        System.out.println("1. LFP (Lithium Iron Phosphate)");
        System.out.println("2. NMC (Nickel Manganese Cobalt)");
        System.out.println("3. NCA (Nickel Cobalt Aluminum)");
        
        String batteryType;
        while (true) {
            System.out.print("Select battery type (1-3): ");
            String choice = scanner.nextLine().trim();
            switch (choice) {
                case "1":
                    batteryType = "LFP";
                    break;
                case "2":
                    batteryType = "NMC";
                    break;
                case "3":
                    batteryType = "NCA";
                    break;
                default:
                    System.out.println("Invalid choice. Please select 1, 2, or 3.");
                    continue;
            }
            break;
        }

        double capacity = getValidatedDoubleInput("Enter battery capacity (kWh): ", this::validateBatteryCapacity);
        
        BatteryProfile profile = new BatteryProfile(capacity, batteryType);
        
        System.out.print("Enter current battery degradation (%, press Enter for 0): ");
        String input = scanner.nextLine().trim();
        if (!input.isEmpty()) {
            try {
                double degradationPercent = Double.parseDouble(input);
                if (degradationPercent >= 0 && degradationPercent <= 100) {
                    double remainingCapacityPercent = 100.0 - degradationPercent;
                    profile.setCurrentCapacity(capacity * (remainingCapacityPercent / 100.0));
                    System.out.printf("Remaining battery capacity: %.1f kWh (%.1f%%)\n", 
                        profile.getCurrentCapacity(), 
                        remainingCapacityPercent);
                }
            } catch (NumberFormatException ignored) {}
        }
        
        return profile;
    }

    private ChargingProfile createChargingProfile(double maxDc, double maxAc) {
        ChargingProfile profile = new ChargingProfile(maxDc, maxAc);
        
        System.out.println("\nEnter charging curve points (empty line to finish):");
        System.out.println("Format: <battery_percentage> <charging_power>");
        System.out.println("Example: 20 150  (means 150kW at 20% battery)");
        
        while (true) {
            System.out.print("\nEnter point (or empty line to finish): ");
            String input = scanner.nextLine().trim();
            if (input.isEmpty()) {
                break;
            }
            
            try {
                String[] parts = input.split("\\s+");
                if (parts.length == 2) {
                    double percentage = Double.parseDouble(parts[0]);
                    double power = Double.parseDouble(parts[1]);
                    
                    if (percentage >= 0 && percentage <= 100 && power >= 0 && power <= maxDc) {
                        profile.addChargingPoint(percentage, power);
                    } else {
                        System.out.println("Invalid values. Percentage must be 0-100, power must be 0-" + maxDc);
                    }
                } else {
                    System.out.println("Invalid format. Please use: percentage power");
                }
            } catch (NumberFormatException e) {
                System.out.println("Invalid numbers. Please try again.");
            }
        }
        
        return profile;
    }

    private String getValidatedInput(String prompt, String fieldName, java.util.function.Function<String, ValidationResult> validator) {
        while (true) {
            System.out.print(prompt);
            String input = scanner.nextLine().trim();
            ValidationResult result = validator.apply(input);
            if (result.isValid()) {
                return input;
            }
            System.out.println(result.getErrors().get(0));
        }
    }

    private double getValidatedDoubleInput(String prompt, java.util.function.Function<Double, ValidationResult> validator) {
        while (true) {
            System.out.print(prompt);
            try {
                double value = Double.parseDouble(scanner.nextLine().trim());
                ValidationResult result = validator.apply(value);
                if (result.isValid()) {
                    return value;
                }
                System.out.println(result.getErrors().get(0));
            } catch (NumberFormatException e) {
                System.out.println("Please enter a valid number.");
            }
        }
    }

    private int getValidatedIntInput(String prompt, java.util.function.Function<Integer, ValidationResult> validator) {
        while (true) {
            System.out.print(prompt);
            try {
                int value = Integer.parseInt(scanner.nextLine().trim());
                ValidationResult result = validator.apply(value);
                if (result.isValid()) {
                    return value;
                }
                System.out.println(result.getErrors().get(0));
            } catch (NumberFormatException e) {
                System.out.println("Please enter a valid number.");
            }
        }
    }

    private boolean wantToAddProfile(String prompt) {
        System.out.print("\n" + prompt);
        return InputCleanerUtil.formatYesOrNoToBoolean(scanner.nextLine().trim());
    }

    // Validation methods
    private ValidationResult validateName(String value) {
        return CarProfileValidator.validateRequiredString(value, "Name");
    }

    private ValidationResult validateYesNo(String value) {
        List<String> errors = new ArrayList<>();
        if (!value.toLowerCase().matches("^(yes|no|y|n)$")) {
            errors.add("Please enter yes/no or y/n");
        }
        return new ValidationResult(errors.isEmpty(), errors);
    }

    private ValidationResult validateBuildYear(Integer year) {
        return CarProfileValidator.validateBuildYear(year);
    }

    private ValidationResult validateBatteryCapacity(Double capacity) {
        return CarProfileValidator.validateBatteryCapacity(capacity);
    }

    private ValidationResult validateWltpRange(Double range) {
        return CarProfileValidator.validateWltpRange(range);
    }

    private ValidationResult validateChargingPower(Double power) {
        return CarProfileValidator.validateChargingPower(power);
    }

    private void deleteProfile() {
        List<CarProfile> profiles = repository.findAll();
        if (profiles.isEmpty()) {
            System.out.println("\nNo car profiles to delete.");
            waitForEnter();
            return;
        }

        System.out.println("\nSelect profile to delete:");
        for (int i = 0; i < profiles.size(); i++) {
            System.out.println((i + 1) + ". " + profiles.get(i).getName());
        }

        System.out.print("\nEnter number (1-" + profiles.size() + "): ");
        try {
            int choice = Integer.parseInt(scanner.nextLine()) - 1;
            if (choice >= 0 && choice < profiles.size()) {
                String id = profiles.get(choice).getId();
                // Clear selected profile if it's being deleted
                if (selectedProfile != null && selectedProfile.getId().equals(id)) {
                    selectedProfile = null;
                }
                repository.delete(id);
                System.out.println("Profile deleted successfully!");
            } else {
                System.out.println("Invalid choice.");
            }
        } catch (NumberFormatException e) {
            System.out.println("Invalid input. Please enter a number.");
        }
        waitForEnter();
    }

    private void editProfile() {
        List<CarProfile> profiles = repository.findAll();
        if (profiles.isEmpty()) {
            System.out.println("\nNo car profiles to edit.");
            waitForEnter();
            return;
        }

        System.out.println("\nSelect profile to edit:");
        for (int i = 0; i < profiles.size(); i++) {
            System.out.println((i + 1) + ". " + profiles.get(i).getName());
        }

        System.out.print("\nEnter number (1-" + profiles.size() + "): ");
        try {
            int choice = Integer.parseInt(scanner.nextLine()) - 1;
            if (choice >= 0 && choice < profiles.size()) {
                CarProfile profileToEdit = profiles.get(choice);
                editProfileDetails(profileToEdit);
                // Update selected profile if it was edited
                if (selectedProfile != null && selectedProfile.getId().equals(profileToEdit.getId())) {
                    selectedProfile = profileToEdit;
                }
            } else {
                System.out.println("Invalid choice.");
            }
        } catch (NumberFormatException e) {
            System.out.println("Invalid input. Please enter a number.");
        }
        waitForEnter();
    }

    private void editProfileDetails(CarProfile profile) {
        System.out.println("\nEditing profile: " + profile.getName());
        System.out.println("(Press Enter to keep current value)");

        System.out.print("Enter new name [" + profile.getName() + "]: ");
        String name = scanner.nextLine();
        if (!name.isEmpty()) {
            profile.setName(InputCleanerUtil.cleanWhitespacesAround(name));
        }

        System.out.print("Enter new manufacturer [" + profile.getManufacturer() + "]: ");
        String manufacturer = scanner.nextLine();
        if (!manufacturer.isEmpty()) {
            profile.setManufacturer(InputCleanerUtil.cleanWhitespacesAround(manufacturer));
        }

        System.out.print("Enter new model [" + profile.getModel() + "]: ");
        String model = scanner.nextLine();
        if (!model.isEmpty()) {
            profile.setModel(InputCleanerUtil.cleanWhitespacesAround(model));
        }

        System.out.print("Enter new build year [" + profile.getBuildYear() + "]: ");
        String buildYear = scanner.nextLine();
        if (!buildYear.isEmpty()) {
            profile.setBuildYear(InputCleanerUtil.cleanIntegerFromCharacters(buildYear));
        }

        System.out.print("Has heat pump? (yes/no) [" + (profile.isHasHeatPump() ? "yes" : "no") + "]: ");
        String hasHeatPump = scanner.nextLine();
        if (!hasHeatPump.isEmpty()) {
            profile.setHasHeatPump(InputCleanerUtil.formatYesOrNoToBoolean(hasHeatPump));
        }

        repository.update(profile);
        System.out.println("Profile updated successfully!");
    }

    private void displayProfile(CarProfile profile) {
        System.out.println("\n----------------------------------------");
        System.out.println("Name: " + profile.getName());
        System.out.println("Manufacturer: " + profile.getManufacturer());
        System.out.println("Model: " + profile.getModel());
        System.out.println("Build Year: " + profile.getBuildYear());
        System.out.println("Heat Pump: " + (profile.isHasHeatPump() ? "Yes" : "No"));
        System.out.println("----------------------------------------");
    }

    private void waitForEnter() {
        System.out.println("\nPress Enter to continue...");
        scanner.nextLine();
    }

    // Getter for the selected profile
    public CarProfile getSelectedProfile() {
        return selectedProfile;
    }
} 
