package org.ulrica.application.usecase;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import org.junit.Before;
import org.junit.Test;
import org.ulrica.application.port.in.CreateCarProfileUseCaseInterface.CreateCarProfileCommand;
import org.ulrica.application.port.out.MockCarProfileRepository;
import org.ulrica.domain.entity.CarProfile;
import org.ulrica.domain.valueobject.BatteryType;

public class CreateCarProfileInteractorTest {

    private CreateCarProfileInteractor interactor;
    private MockCarProfileRepository repository;

    @Before
    public void setUp() {
        repository = new MockCarProfileRepository();
        interactor = new CreateCarProfileInteractor(repository);
    }

    @Test
    public void testCreateCarProfile() {
        
        CreateCarProfileCommand command = new CreateCarProfileCommand(
            "Test Car",
            "Test Manufacturer",
            "Test Model",
            2023,
            true,
            500.0,
            250.0,
            11.0,
            "NMC",
            80.0,
            5.0,
            15.0,
            20.0,
            25.0
        );
        
        
        CarProfile result = interactor.createCarProfile(command);
        
        
        assertNotNull(result);
        assertNotNull(result.getId()); 
        assertEquals(command.getName(), result.getName());
        assertEquals(command.getManufacturer(), result.getManufacturer());
        assertEquals(command.getModel(), result.getModel());
        assertEquals(command.getYear(), result.getYear());
        assertEquals(command.hasHeatPump(), result.hasHeatPump());
        assertEquals(command.getWltpRangeKm(), result.getWltpRangeKm(), 0.01);
        assertEquals(command.getMaxDcPowerKw(), result.getMaxDcPowerKw(), 0.01);
        assertEquals(command.getMaxAcPowerKw(), result.getMaxAcPowerKw(), 0.01);
        
        
        assertNotNull(result.getBatteryProfile());
        assertEquals(BatteryType.valueOf(command.getBatteryType()), result.getBatteryProfile().getType());
        assertEquals(command.getBatteryCapacityKwh(), result.getBatteryProfile().getCapacityKwh(), 0.01);
        assertEquals(command.getBatteryDegradationPercent(), result.getBatteryProfile().getDegradationPercent(), 0.01);
        
        
        assertNotNull(result.getConsumptionProfile());
        assertEquals(command.getConsumptionAt50Kmh(), result.getConsumptionProfile().getConsumptionAt50Kmh(), 0.01);
        assertEquals(command.getConsumptionAt100Kmh(), result.getConsumptionProfile().getConsumptionAt100Kmh(), 0.01);
        assertEquals(command.getConsumptionAt130Kmh(), result.getConsumptionProfile().getConsumptionAt130Kmh(), 0.01);
        
        
        assertEquals(1, repository.getSaveCount());
        assertTrue(repository.containsProfile(result.getId()));
    }
    
    @Test
    public void testCreateMultipleProfiles() {
        
        CreateCarProfileCommand command1 = new CreateCarProfileCommand(
            "Car 1",
            "Manufacturer 1",
            "Model 1",
            2021,
            true,
            500.0,
            250.0,
            11.0,
            "NMC",
            80.0,
            5.0,
            15.0,
            20.0,
            25.0
        );
        
        CreateCarProfileCommand command2 = new CreateCarProfileCommand(
            "Car 2",
            "Manufacturer 2",
            "Model 2",
            2022,
            false,
            400.0,
            150.0,
            7.4,
            "LFP",
            60.0,
            2.0,
            14.0,
            18.0,
            22.0
        );
        
        
        CarProfile result1 = interactor.createCarProfile(command1);
        CarProfile result2 = interactor.createCarProfile(command2);
        
        
        assertNotNull(result1);
        assertNotNull(result2);
        
        
        assertTrue(!result1.getId().equals(result2.getId()));
        
        
        assertEquals(2, repository.getProfileCount());
        
        
        assertEquals(2, repository.getSaveCount());
    }
    
    @Test(expected = IllegalArgumentException.class)
    public void testCreateCarProfile_InvalidBatteryType() {
        
        CreateCarProfileCommand command = new CreateCarProfileCommand(
            "Test Car",
            "Test Manufacturer",
            "Test Model",
            2023,
            true,
            500.0,
            250.0,
            11.0,
            "INVALID_TYPE", 
            80.0,
            5.0,
            15.0,
            20.0,
            25.0
        );
        
        
        interactor.createCarProfile(command);
    }
} 