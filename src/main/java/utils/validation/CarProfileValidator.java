package utils.validation;

import java.time.Year;
import java.util.ArrayList;
import java.util.List;

public class CarProfileValidator {
    private static final double MIN_BATTERY_CAPACITY = 10.0;
    private static final double MAX_BATTERY_CAPACITY = 200.0;
    private static final double MAX_CHARGING_POWER = 400.0; // kW
    private static final int MIN_BUILD_YEAR = 1990;
    private static final double MAX_WLTP_RANGE = 1000.0; // km

    public static class ValidationResult {
        private final boolean isValid;
        private final List<String> errors;

        public ValidationResult(boolean isValid, List<String> errors) {
            this.isValid = isValid;
            this.errors = errors;
        }

        public boolean isValid() { return isValid; }
        public List<String> getErrors() { return new ArrayList<>(errors); }
    }

    public static ValidationResult validateBatteryCapacity(double capacity) {
        List<String> errors = new ArrayList<>();
        if (capacity < MIN_BATTERY_CAPACITY || capacity > MAX_BATTERY_CAPACITY) {
            errors.add(String.format("Battery capacity must be between %.1f and %.1f kWh", 
                MIN_BATTERY_CAPACITY, MAX_BATTERY_CAPACITY));
        }
        return new ValidationResult(errors.isEmpty(), errors);
    }

    public static ValidationResult validateChargingPower(double power) {
        List<String> errors = new ArrayList<>();
        if (power < 0 || power > MAX_CHARGING_POWER) {
            errors.add(String.format("Charging power must be between 0 and %.1f kW", MAX_CHARGING_POWER));
        }
        return new ValidationResult(errors.isEmpty(), errors);
    }

    public static ValidationResult validateBuildYear(int year) {
        List<String> errors = new ArrayList<>();
        int currentYear = Year.now().getValue();
        if (year < MIN_BUILD_YEAR || year > currentYear) {
            errors.add(String.format("Build year must be between %d and %d", MIN_BUILD_YEAR, currentYear));
        }
        return new ValidationResult(errors.isEmpty(), errors);
    }

    public static ValidationResult validateWltpRange(double range) {
        List<String> errors = new ArrayList<>();
        if (range <= 0 || range > MAX_WLTP_RANGE) {
            errors.add(String.format("WLTP range must be between 0 and %.1f km", MAX_WLTP_RANGE));
        }
        return new ValidationResult(errors.isEmpty(), errors);
    }

    public static ValidationResult validateRequiredString(String value, String fieldName) {
        List<String> errors = new ArrayList<>();
        if (value == null || value.trim().isEmpty()) {
            errors.add(fieldName + " is required and cannot be empty");
        }
        return new ValidationResult(errors.isEmpty(), errors);
    }

    public static ValidationResult validateConsumption(double consumption) {
        List<String> errors = new ArrayList<>();
        if (consumption <= 0 || consumption > 100) { // Assuming max 100 kWh/100km
            errors.add("Consumption must be between 0 and 100 kWh/100km");
        }
        return new ValidationResult(errors.isEmpty(), errors);
    }
} 