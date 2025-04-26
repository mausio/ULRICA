package org.ulrica.application.service;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import org.junit.Before;
import org.junit.Test;
import org.ulrica.application.port.in.InputValidationServiceInterface;

public class InputValidationServiceTest {
    
    private InputValidationServiceInterface validationService;
    
    @Before
    public void setUp() {
        validationService = new InputValidationService();
    }
    
    @Test
    public void testIsValidName() {
        // Valid case
        assertTrue(validationService.isValidName("Valid Name"));
        
        // Invalid case
        assertFalse(validationService.isValidName("Invalid@Name"));
    }
    
    @Test
    public void testIsValidYear() {
        // Valid case
        assertTrue(validationService.isValidYear(2022));
        
        // Invalid case
        assertFalse(validationService.isValidYear(1800));
    }
    
    @Test
    public void testIsValidBatteryCapacity() {
        // Valid case
        assertTrue(validationService.isValidBatteryCapacity(75.0));
        
        // Invalid case
        assertFalse(validationService.isValidBatteryCapacity(5.0));
    }
    
    @Test
    public void testIsValidBatteryDegradation() {
        // Valid case
        assertTrue(validationService.isValidBatteryDegradation(5.0));
        
        // Invalid case
        assertFalse(validationService.isValidBatteryDegradation(101.0));
    }
    
    @Test
    public void testIsValidDcChargingPower() {
        // Valid case
        assertTrue(validationService.isValidDcChargingPower(150.0));
        
        // Invalid case
        assertFalse(validationService.isValidDcChargingPower(5.0));
    }
    
    @Test
    public void testIsValidAcChargingPower() {
        // Valid case
        assertTrue(validationService.isValidAcChargingPower(11.0));
        
        // Invalid case
        assertFalse(validationService.isValidAcChargingPower(0.5));
    }
    
    @Test
    public void testIsValidConsumption() {
        // Valid case
        assertTrue(validationService.isValidConsumption(20.0));
        
        // Invalid case
        assertFalse(validationService.isValidConsumption(0.5));
    }
    
    @Test
    public void testIsValidConsumptionProgression() {
        // Valid case
        assertTrue(validationService.isValidConsumptionProgression(15.0, 18.0, 22.0));
        
        // Invalid case
        assertFalse(validationService.isValidConsumptionProgression(15.0, 22.0, 18.0));
    }
    
    @Test
    public void testIsValidBatteryTypeChoice() {
        // Valid case - assuming there are at least 3 battery types
        assertTrue(validationService.isValidBatteryTypeChoice(1));
        
        // Invalid case
        assertFalse(validationService.isValidBatteryTypeChoice(0));
    }
    
    @Test
    public void testGetValidationMessage() {
        // Test a specific message
        String message = validationService.getValidationMessage("name", "Invalid@Name");
        assertTrue(message.contains("Invalid name"));
    }
} 