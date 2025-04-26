package org.ulrica.application.port.out;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.ulrica.domain.entity.CarProfile;
import org.ulrica.domain.valueobject.BatteryProfile;
import org.ulrica.domain.valueobject.BatteryType;
import org.ulrica.domain.valueobject.ConsumptionProfile;

/**
 * A mock implementation of CarProfilePersistencePortInterface for testing.
 * Stores car profiles in memory and tracks operation counts for verification.
 */
public class MockCarProfileRepository implements CarProfilePersistencePortInterface {

    private final Map<String, CarProfile> profiles = new HashMap<>();
    private int saveCount = 0;
    private int deleteCount = 0;
    private int findCount = 0;
    
    /**
     * Create an empty mock repository
     */
    public MockCarProfileRepository() {
        // Initialize with no profiles
    }
    
    /**
     * Create a mock repository with some initial test profiles
     * @param includeTestData Whether to include sample profiles
     */
    public MockCarProfileRepository(boolean includeTestData) {
        if (includeTestData) {
            addTestProfiles();
        }
    }
    
    /**
     * Adds some sample car profiles for testing
     */
    private void addTestProfiles() {
        // Test profile 1
        CarProfile profile1 = new CarProfile.Builder()
            .id("test-id-1")
            .name("Test EV 1")
            .manufacturer("Test Manufacturer")
            .model("Model X")
            .year(2023)
            .hasHeatPump(true)
            .wltpRangeKm(500)
            .maxDcPowerKw(250.0)
            .maxAcPowerKw(11.0)
            .batteryProfile(new BatteryProfile(
                BatteryType.NMC, 
                80.0, 
                5.0, 
                250.0, 
                11.0
            ))
            .consumptionProfile(new ConsumptionProfile(15.0, 20.0, 25.0))
            .build();
        
        // Test profile 2
        CarProfile profile2 = new CarProfile.Builder()
            .id("test-id-2")
            .name("Test EV 2")
            .manufacturer("Test Manufacturer")
            .model("Model Y")
            .year(2023)
            .hasHeatPump(false)
            .wltpRangeKm(400)
            .maxDcPowerKw(150.0)
            .maxAcPowerKw(11.0)
            .batteryProfile(new BatteryProfile(
                BatteryType.LFP, 
                70.0, 
                2.0, 
                150.0, 
                11.0
            ))
            .consumptionProfile(new ConsumptionProfile(14.0, 18.0, 22.0))
            .build();
        
        // Add to map
        profiles.put(profile1.getId(), profile1);
        profiles.put(profile2.getId(), profile2);
    }

    @Override
    public List<CarProfile> findAll() {
        findCount++;
        return new ArrayList<>(profiles.values());
    }

    @Override
    public Optional<CarProfile> findById(String id) {
        findCount++;
        return Optional.ofNullable(profiles.get(id));
    }

    @Override
    public CarProfile save(CarProfile profile) {
        saveCount++;
        profiles.put(profile.getId(), profile);
        return profile;
    }

    @Override
    public void delete(String id) {
        deleteCount++;
        profiles.remove(id);
    }
    
    /**
     * Get the number of profiles in the repository
     * @return Count of stored profiles
     */
    public int getProfileCount() {
        return profiles.size();
    }
    
    /**
     * Get the number of times save was called
     * @return Count of save operations
     */
    public int getSaveCount() {
        return saveCount;
    }
    
    /**
     * Get the number of times delete was called
     * @return Count of delete operations
     */
    public int getDeleteCount() {
        return deleteCount;
    }
    
    /**
     * Get the number of times find operations were called
     * @return Count of find operations
     */
    public int getFindCount() {
        return findCount;
    }
    
    /**
     * Reset all the operation counters
     */
    public void resetCounters() {
        saveCount = 0;
        deleteCount = 0;
        findCount = 0;
    }
    
    /**
     * Clear all profiles from the repository
     */
    public void clear() {
        profiles.clear();
    }
    
    /**
     * Check if the profile exists in the repository
     * @param id The profile ID to check
     * @return true if the profile exists, false otherwise
     */
    public boolean containsProfile(String id) {
        return profiles.containsKey(id);
    }
} 