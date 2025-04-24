package org.ulrica.infrastructure.util;

import org.ulrica.application.port.in.ValidationInterface;
import org.ulrica.domain.valueobject.BatteryType;

public final class ValidationUtils implements ValidationInterface {
    private static final ValidationUtils INSTANCE = new ValidationUtils();
    
    // Constants for validation
    private static final int MIN_NAME_LENGTH = 1;
    private static final int MAX_NAME_LENGTH = 20;
    private static final double MIN_BATTERY_CAPACITY = 10.0;
    private static final double MAX_BATTERY_CAPACITY = 300.0;
    private static final double MIN_DC_CHARGING_POWER = 10.0;
    private static final double MAX_DC_CHARGING_POWER = 1000.0;
    private static final double MIN_AC_CHARGING_POWER = 1.0;
    private static final double MAX_AC_CHARGING_POWER = 50.0;
    private static final double MIN_CONSUMPTION = 1.0;
    private static final double MAX_CONSUMPTION = 50.0;
    private static final int MIN_YEAR = 1886; // First car was built in 1886
    private static final int MAX_YEAR = java.time.Year.now().getValue();
    
    private ValidationUtils() {} // Prevent instantiation
    
    public static ValidationInterface getInstance() {
        return INSTANCE;
    }

    @Override
    public boolean isValidName(String name) {
        if (name == null || name.trim().isEmpty()) {
            return false;
        }
        String trimmed = name.trim();
        return trimmed.length() >= MIN_NAME_LENGTH && 
               trimmed.length() <= MAX_NAME_LENGTH && 
               trimmed.matches("^[a-zA-Z0-9 ]+$");
    }

    @Override
    public boolean isValidYear(int year) {
        return year >= MIN_YEAR && year <= MAX_YEAR;
    }

    @Override
    public boolean isValidBatteryCapacity(double capacity) {
        return capacity >= MIN_BATTERY_CAPACITY && capacity <= MAX_BATTERY_CAPACITY;
    }

    @Override
    public boolean isValidBatteryDegradation(double degradation) {
        return degradation >= 0.0 && degradation <= 100.0;
    }

    @Override
    public boolean isValidDcChargingPower(double power) {
        return power >= MIN_DC_CHARGING_POWER && power <= MAX_DC_CHARGING_POWER;
    }

    @Override
    public boolean isValidAcChargingPower(double power) {
        return power >= MIN_AC_CHARGING_POWER && power <= MAX_AC_CHARGING_POWER;
    }

    @Override
    public boolean isValidConsumption(double consumption) {
        return consumption >= MIN_CONSUMPTION && consumption <= MAX_CONSUMPTION;
    }

    @Override
    public boolean isValidConsumptionProgression(double at50, double at100, double at130) {
        return at50 <= at100 && at100 <= at130;
    }

    @Override
    public boolean isValidBatteryTypeChoice(int choice) {
        return choice >= 1 && choice <= BatteryType.values().length;
    }

    @Override
    public String getValidationMessage(String field, String value) {
        switch (field.toLowerCase()) {
            case "name":
            case "manufacturer":
            case "model":
                return String.format("Invalid %s. Must be between %d and %d characters long and contain only letters, numbers, and spaces.", 
                    field, MIN_NAME_LENGTH, MAX_NAME_LENGTH);
            case "year":
                return String.format("Invalid year. Must be between %d and %d.", MIN_YEAR, MAX_YEAR);
            case "battery type":
                return String.format("Invalid battery type. Please select a number between 1 and %d.", BatteryType.values().length);
            case "battery capacity":
                return String.format("Invalid battery capacity. Must be between %.1f and %.1f kWh.", 
                    MIN_BATTERY_CAPACITY, MAX_BATTERY_CAPACITY);
            case "battery degradation":
                return "Invalid battery degradation. Must be between 0 and 100 percent.";
            case "dc charging power":
                return String.format("Invalid DC charging power. Must be between %.1f and %.1f kW.", 
                    MIN_DC_CHARGING_POWER, MAX_DC_CHARGING_POWER);
            case "ac charging power":
                return String.format("Invalid AC charging power. Must be between %.1f and %.1f kW.", 
                    MIN_AC_CHARGING_POWER, MAX_AC_CHARGING_POWER);
            case "consumption":
                return String.format("Invalid consumption. Must be between %.1f and %.1f kWh/100km.", 
                    MIN_CONSUMPTION, MAX_CONSUMPTION);
            case "consumption progression":
                return "Invalid consumption progression. Consumption must increase with speed.";
            default:
                return "Invalid input.";
        }
    }
} 