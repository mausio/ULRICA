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
        
        assertTrue(validator.isValidName("Test Name"));
        assertTrue(validator.isValidName("Model-123"));
        assertTrue(validator.isValidName("a"));
        assertTrue(validator.isValidName("Valid20CharNameValid"));
        
        
        assertFalse(validator.isValidName(null));
        assertFalse(validator.isValidName(""));
        assertFalse(validator.isValidName("   "));
        assertFalse(validator.isValidName("Invalid@Name")); 
        assertFalse(validator.isValidName("This name is way too long and should be invalid")); 
    }
    
    @Test
    public void testIsValidYear() {
        
        assertTrue(validator.isValidYear(1886)); 
        assertTrue(validator.isValidYear(2000));
        assertTrue(validator.isValidYear(java.time.Year.now().getValue())); 
        
        
        assertFalse(validator.isValidYear(1885)); 
        assertFalse(validator.isValidYear(java.time.Year.now().getValue() + 1)); 
    }
    
    @Test
    public void testIsValidBatteryCapacity() {
        
        assertTrue(validator.isValidBatteryCapacity(10.0)); 
        assertTrue(validator.isValidBatteryCapacity(75.0));
        assertTrue(validator.isValidBatteryCapacity(300.0)); 
        
        
        assertFalse(validator.isValidBatteryCapacity(9.9)); 
        assertFalse(validator.isValidBatteryCapacity(300.1)); 
    }
    
    @Test
    public void testIsValidBatteryDegradation() {
        
        assertTrue(validator.isValidBatteryDegradation(0.0)); 
        assertTrue(validator.isValidBatteryDegradation(5.5));
        assertTrue(validator.isValidBatteryDegradation(100.0)); 
        
        
        assertFalse(validator.isValidBatteryDegradation(-0.1)); 
        assertFalse(validator.isValidBatteryDegradation(100.1)); 
    }
    
    @Test
    public void testIsValidDcChargingPower() {
        
        assertTrue(validator.isValidDcChargingPower(10.0)); 
        assertTrue(validator.isValidDcChargingPower(150.0));
        assertTrue(validator.isValidDcChargingPower(1000.0)); 
        
        
        assertFalse(validator.isValidDcChargingPower(9.9)); 
        assertFalse(validator.isValidDcChargingPower(1000.1)); 
    }
    
    @Test
    public void testIsValidAcChargingPower() {
        
        assertTrue(validator.isValidAcChargingPower(1.0)); 
        assertTrue(validator.isValidAcChargingPower(11.0));
        assertTrue(validator.isValidAcChargingPower(50.0)); 
        
        
        assertFalse(validator.isValidAcChargingPower(0.9)); 
        assertFalse(validator.isValidAcChargingPower(50.1)); 
    }
    
    @Test
    public void testIsValidConsumption() {
        
        assertTrue(validator.isValidConsumption(1.0)); 
        assertTrue(validator.isValidConsumption(20.0));
        assertTrue(validator.isValidConsumption(50.0)); 
        
        
        assertFalse(validator.isValidConsumption(0.9)); 
        assertFalse(validator.isValidConsumption(50.1)); 
    }
    
    @Test
    public void testIsValidConsumptionProgression() {
        
        assertTrue(validator.isValidConsumptionProgression(15.0, 18.0, 22.0));
        assertTrue(validator.isValidConsumptionProgression(15.0, 15.0, 15.0)); 
        
        
        assertFalse(validator.isValidConsumptionProgression(16.0, 15.0, 22.0)); 
        assertFalse(validator.isValidConsumptionProgression(15.0, 22.0, 20.0)); 
    }
    
    @Test
    public void testIsValidBatteryTypeChoice() {
        
        int batteryTypeCount = org.ulrica.domain.valueobject.BatteryType.values().length;
        
        
        assertTrue(validator.isValidBatteryTypeChoice(1)); 
        assertTrue(validator.isValidBatteryTypeChoice(batteryTypeCount)); 
        
        
        assertFalse(validator.isValidBatteryTypeChoice(0)); 
        assertFalse(validator.isValidBatteryTypeChoice(batteryTypeCount + 1)); 
    }
    
    @Test
    public void testGetValidationMessage() {
        
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
        assertNotNull(validator.getValidationMessage("unknown field", "")); 
    }
} 