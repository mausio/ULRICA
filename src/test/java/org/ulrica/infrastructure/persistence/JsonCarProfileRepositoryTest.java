package org.ulrica.infrastructure.persistence;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;
import org.ulrica.domain.entity.CarProfile;
import org.ulrica.domain.valueobject.BatteryProfile;
import org.ulrica.domain.valueobject.BatteryType;
import org.ulrica.domain.valueobject.ConsumptionProfile;


public class JsonCarProfileRepositoryTest {
    
    @Rule
    public TemporaryFolder tempFolder = new TemporaryFolder();
    
    private MockJsonCarProfileRepository repository;
    
    @Before
    public void setUp() throws Exception {
        File testDir = tempFolder.newFolder("test_data");
        repository = new MockJsonCarProfileRepository(testDir.getPath());
    }
    
    private CarProfile createTestCarProfile() {
        String id = UUID.randomUUID().toString();
        
        BatteryProfile batteryProfile = new BatteryProfile(
            BatteryType.NMC,
            75.0,  
            5.0,   
            150.0, 
            11.0   
        );
        
        ConsumptionProfile consumptionProfile = new ConsumptionProfile(
            15.0,  
            18.0,  
            22.0   
        );
        
        return new CarProfile.Builder()
                .id(id)
                .name("Test Car")
                .manufacturer("Test Manufacturer")
                .model("Test Model")
                .year(2023)
                .batteryProfile(batteryProfile)
                .consumptionProfile(consumptionProfile)
                .hasHeatPump(true)
                .wltpRangeKm(450)
                .build();
    }
    
    @Test
    public void testSaveAndFindById() {
        
        CarProfile profile = createTestCarProfile();
        
        
        repository.save(profile);
        Optional<CarProfile> retrieved = repository.findById(profile.getId());
        
        
        assertTrue(retrieved.isPresent());
        assertEquals(profile.getId(), retrieved.get().getId());
        assertEquals(profile.getName(), retrieved.get().getName());
    }
    
    @Test
    public void testFindAll() {
        
        CarProfile profile1 = createTestCarProfile();
        CarProfile profile2 = createTestCarProfile();
        
        
        repository.save(profile1);
        repository.save(profile2);
        List<CarProfile> profiles = repository.findAll();
        
        
        assertEquals(2, profiles.size());
    }
    
    @Test
    public void testUpdate() {
        
        CarProfile profile = createTestCarProfile();
        repository.save(profile);
        
        
        CarProfile updatedProfile = new CarProfile.Builder()
                .id(profile.getId())
                .name("Updated Car")
                .manufacturer(profile.getManufacturer())
                .model(profile.getModel())
                .year(profile.getYear())
                .batteryProfile(profile.getBatteryProfile())
                .consumptionProfile(profile.getConsumptionProfile())
                .hasHeatPump(profile.hasHeatPump())
                .wltpRangeKm(profile.getWltpRangeKm())
                .build();
        
        repository.save(updatedProfile);
        Optional<CarProfile> retrieved = repository.findById(profile.getId());
        
        
        assertTrue(retrieved.isPresent());
        assertEquals("Updated Car", retrieved.get().getName());
    }
    
    @Test
    public void testDelete() {
        
        CarProfile profile = createTestCarProfile();
        repository.save(profile);
        
        
        repository.delete(profile.getId());
        Optional<CarProfile> retrieved = repository.findById(profile.getId());
        
        
        assertFalse(retrieved.isPresent());
    }
    
    @Test
    public void testFindByIdNonExistent() {
        
        Optional<CarProfile> retrieved = repository.findById("non-existent-id");
        
        
        assertFalse(retrieved.isPresent());
    }
    
    private static class MockJsonCarProfileRepository extends JsonCarProfileRepository {
        
        public MockJsonCarProfileRepository(String storagePath) {
            
            
            super(storagePath, "test_car_profiles.json");
        }
    }
} 