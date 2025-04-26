package org.ulrica.infrastructure.util;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import org.junit.Test;
import org.ulrica.application.port.in.ValidationInterface;

public class ValidationUtilsTest {
    
    private final ValidationInterface validator = ValidationUtils.getInstance();
    
    @Test
    public void testSingletonPattern() {
        ValidationInterface instance1 = ValidationUtils.getInstance();
        ValidationInterface instance2 = ValidationUtils.getInstance();
        
        assertTrue("Singleton instances should be the same", instance1 == instance2);
    }
    
    @Test
    public void testIsValidName() {
        // Valid cases
        assertTrue(validator.isValidName("Test Name"));
        assertTrue(validator.isValidName("Model-123"));
        assertTrue(validator.isValidName("a"));
        assertTrue(validator.isValidName("Valid20CharNameValid"));
        
        // Invalid cases
        assertFalse(validator.isValidName(null));
        assertFalse(validator.isValidName(""));
        assertFalse(validator.isValidName("   "));
        assertFalse(validator.isValidName("Invalid@Name")); // Special characters
        assertFalse(validator.isValidName("This name is way too long and should be invalid")); // Too long
    }
    
    @Test
    public void testIsValidYear() {
        // Valid cases
        assertTrue(validator.isValidYear(1886)); // Min year
        assertTrue(validator.isValidYear(2000));
        assertTrue(validator.isValidYear(java.time.Year.now().getValue())); // Current year
        
        // Invalid cases
        assertFalse(validator.isValidYear(1885)); // Before min year
        assertFalse(validator.isValidYear(java.time.Year.now().getValue() + 1)); // Future year
    }
    
    @Test
    public void testIsValidBatteryCapacity() {
        // Valid cases
        assertTrue(validator.isValidBatteryCapacity(10.0)); // Min
        assertTrue(validator.isValidBatteryCapacity(75.0));
        assertTrue(validator.isValidBatteryCapacity(300.0)); // Max
        
        // Invalid cases
        assertFalse(validator.isValidBatteryCapacity(9.9)); // Below min
        assertFalse(validator.isValidBatteryCapacity(300.1)); // Above max
    }
    
    @Test
    public void testIsValidBatteryDegradation() {
        // Valid cases
        assertTrue(validator.isValidBatteryDegradation(0.0)); // Min
        assertTrue(validator.isValidBatteryDegradation(5.5));
        assertTrue(validator.isValidBatteryDegradation(100.0)); // Max
        
        // Invalid cases
        assertFalse(validator.isValidBatteryDegradation(-0.1)); // Below min
        assertFalse(validator.isValidBatteryDegradation(100.1)); // Above max
    }
    
    @Test
    public void testIsValidDcChargingPower() {
        // Valid cases
        assertTrue(validator.isValidDcChargingPower(10.0)); // Min
        assertTrue(validator.isValidDcChargingPower(150.0));
        assertTrue(validator.isValidDcChargingPower(1000.0)); // Max
        
        // Invalid cases
        assertFalse(validator.isValidDcChargingPower(9.9)); // Below min
        assertFalse(validator.isValidDcChargingPower(1000.1)); // Above max
    }
    
    @Test
    public void testIsValidAcChargingPower() {
        // Valid cases
        assertTrue(validator.isValidAcChargingPower(1.0)); // Min
        assertTrue(validator.isValidAcChargingPower(11.0));
        assertTrue(validator.isValidAcChargingPower(50.0)); // Max
        
        // Invalid cases
        assertFalse(validator.isValidAcChargingPower(0.9)); // Below min
        assertFalse(validator.isValidAcChargingPower(50.1)); // Above max
    }
    
    @Test
    public void testIsValidConsumption() {
        // Valid cases
        assertTrue(validator.isValidConsumption(1.0)); // Min
        assertTrue(validator.isValidConsumption(20.0));
        assertTrue(validator.isValidConsumption(50.0)); // Max
        
        // Invalid cases
        assertFalse(validator.isValidConsumption(0.9)); // Below min
        assertFalse(validator.isValidConsumption(50.1)); // Above max
    }
    
    @Test
    public void testIsValidConsumptionProgression() {
        // Valid cases - consumption increases with speed
        assertTrue(validator.isValidConsumptionProgression(15.0, 18.0, 22.0));
        assertTrue(validator.isValidConsumptionProgression(15.0, 15.0, 15.0)); // Equal values
        
        // Invalid cases - consumption decreases with speed
        assertFalse(validator.isValidConsumptionProgression(16.0, 15.0, 22.0)); // at50 > at100
        assertFalse(validator.isValidConsumptionProgression(15.0, 22.0, 20.0)); // at100 > at130
    }
    
    @Test
    public void testIsValidBatteryTypeChoice() {
        // Valid cases depend on the number of battery types
        int batteryTypeCount = org.ulrica.domain.valueobject.BatteryType.values().length;
        
        // Valid cases
        assertTrue(validator.isValidBatteryTypeChoice(1)); // First type
        assertTrue(validator.isValidBatteryTypeChoice(batteryTypeCount)); // Last type
        
        // Invalid cases
        assertFalse(validator.isValidBatteryTypeChoice(0)); // Below range
        assertFalse(validator.isValidBatteryTypeChoice(batteryTypeCount + 1)); // Above range
    }
    
    @Test
    public void testGetValidationMessage() {
        // Test that validation messages are returned for all field types
        assertNotNull(validator.getValidationMessage("name", ""));
        assertNotNull(validator.getValidationMessage("manufacturer", ""));
        assertNotNull(validator.getValidationMessage("model", ""));
        assertNotNull(validator.getValidationMessage("year", ""));
        assertNotNull(validator.getValidationMessage("battery type", ""));
        assertNotNull(validator.getValidationMessage("battery capacity", ""));
        assertNotNull(validator.getValidationMessage("battery degradation", ""));
        assertNotNull(validator.getValidationMessage("dc charging power", ""));
        assertNotNull(validator.getValidationMessage("ac charging power", ""));
        assertNotNull(validator.getValidationMessage("consumption", ""));
        assertNotNull(validator.getValidationMessage("consumption progression", ""));
        assertNotNull(validator.getValidationMessage("unknown field", "")); // default case
    }
} 