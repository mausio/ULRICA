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
        
        assertTrue(validationService.isValidName("Valid Name"));
        
        
        assertFalse(validationService.isValidName("Invalid@Name"));
    }
    
    @Test
    public void testIsValidYear() {
        
        assertTrue(validationService.isValidYear(2022));
        
        
        assertFalse(validationService.isValidYear(1800));
    }
    
    @Test
    public void testIsValidBatteryCapacity() {
        
        assertTrue(validationService.isValidBatteryCapacity(75.0));
        
        
        assertFalse(validationService.isValidBatteryCapacity(5.0));
    }
    
    @Test
    public void testIsValidBatteryDegradation() {
        
        assertTrue(validationService.isValidBatteryDegradation(5.0));
        
        
        assertFalse(validationService.isValidBatteryDegradation(101.0));
    }
    
    @Test
    public void testIsValidDcChargingPower() {
        
        assertTrue(validationService.isValidDcChargingPower(150.0));
        
        
        assertFalse(validationService.isValidDcChargingPower(5.0));
    }
    
    @Test
    public void testIsValidAcChargingPower() {
        
        assertTrue(validationService.isValidAcChargingPower(11.0));
        
        
        assertFalse(validationService.isValidAcChargingPower(0.5));
    }
    
    @Test
    public void testIsValidConsumption() {
        
        assertTrue(validationService.isValidConsumption(20.0));
        
        
        assertFalse(validationService.isValidConsumption(0.5));
    }
    
    @Test
    public void testIsValidConsumptionProgression() {
        
        assertTrue(validationService.isValidConsumptionProgression(15.0, 18.0, 22.0));
        
        
        assertFalse(validationService.isValidConsumptionProgression(15.0, 22.0, 18.0));
    }
    
    @Test
    public void testIsValidBatteryTypeChoice() {
        
        assertTrue(validationService.isValidBatteryTypeChoice(1));
        
        
        assertFalse(validationService.isValidBatteryTypeChoice(0));
    }
    
    @Test
    public void testGetValidationMessage() {
        
        String message = validationService.getValidationMessage("name", "Invalid@Name");
        assertTrue(message.contains("Invalid name"));
    }
} 