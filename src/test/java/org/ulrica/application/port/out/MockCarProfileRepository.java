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

public class MockCarProfileRepository implements CarProfilePersistencePortInterface {

    private final Map<String, CarProfile> profiles = new HashMap<>();
    private int saveCount = 0;
    private int deleteCount = 0;
    private int findCount = 0;
    
    public MockCarProfileRepository() {
        
    }
    
    public MockCarProfileRepository(boolean includeTestData) {
        if (includeTestData) {
            addTestProfiles();
        }
    }
    
    private void addTestProfiles() {
        
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
    
    public int getProfileCount() {
        return profiles.size();
    }
    
    public int getSaveCount() {
        return saveCount;
    }
    
    public int getDeleteCount() {
        return deleteCount;
    }
    
    public int getFindCount() {
        return findCount;
    }
    
    public void resetCounters() {
        saveCount = 0;
        deleteCount = 0;
        findCount = 0;
    }
    
    public void clear() {
        profiles.clear();
    }
    
    public boolean containsProfile(String id) {
        return profiles.containsKey(id);
    }
} 