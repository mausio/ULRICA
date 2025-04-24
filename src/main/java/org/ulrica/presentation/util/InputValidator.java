package org.ulrica.presentation.util;

import org.ulrica.application.port.in.ValidationInterface;
import org.ulrica.infrastructure.util.ValidationUtils;

/**
 * @deprecated Use ValidationInterface from application layer instead
 */
@Deprecated
public final class InputValidator {
    private static final ValidationInterface VALIDATOR = ValidationUtils.getInstance();
    
    private InputValidator() {} // Prevent instantiation

    public static boolean isValidName(String name) {
        return VALIDATOR.isValidName(name);
    }

    public static boolean isValidYear(int year) {
        return VALIDATOR.isValidYear(year);
    }

    public static boolean isValidBatteryCapacity(double capacity) {
        return VALIDATOR.isValidBatteryCapacity(capacity);
    }

    public static boolean isValidBatteryDegradation(double degradation) {
        return VALIDATOR.isValidBatteryDegradation(degradation);
    }

    public static boolean isValidDcChargingPower(double power) {
        return VALIDATOR.isValidDcChargingPower(power);
    }

    public static boolean isValidAcChargingPower(double power) {
        return VALIDATOR.isValidAcChargingPower(power);
    }

    public static boolean isValidConsumption(double consumption) {
        return VALIDATOR.isValidConsumption(consumption);
    }

    public static boolean isValidConsumptionProgression(double consumption50, double consumption100, double consumption130) {
        return VALIDATOR.isValidConsumptionProgression(consumption50, consumption100, consumption130);
    }

    public static boolean isValidBatteryTypeChoice(int choice) {
        return VALIDATOR.isValidBatteryTypeChoice(choice);
    }

    public static String getValidationMessage(String field, String value) {
        return VALIDATOR.getValidationMessage(field, value);
    }
} 