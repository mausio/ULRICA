package org.ulrica.domain.valueobject;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertThrows;
import static org.junit.Assert.assertTrue;
import org.junit.Test;

public class BatteryProfileTest {

    @Test
    public void testConstructorAndGetters() {
        
        BatteryType type = BatteryType.NMC;
        double capacityKwh = 80.0;
        double degradationPercent = 5.0;
        double maxDcPowerKw = 250.0;
        double maxAcPowerKw = 11.0;
        
        
        BatteryProfile batteryProfile = new BatteryProfile(
            type, capacityKwh, degradationPercent, maxDcPowerKw, maxAcPowerKw
        );
        
        
        assertEquals(type, batteryProfile.getType());
        assertEquals(capacityKwh, batteryProfile.getCapacityKwh(), 0.001);
        assertEquals(degradationPercent, batteryProfile.getDegradationPercent(), 0.001);
        assertEquals(maxDcPowerKw, batteryProfile.getMaxDcPowerKw(), 0.001);
        assertEquals(maxAcPowerKw, batteryProfile.getMaxAcPowerKw(), 0.001);
    }
    
    @Test
    public void testRemainingCapacity() {
        
        double capacityKwh = 80.0;
        double degradationPercent = 5.0;
        BatteryProfile batteryProfile = new BatteryProfile(
            BatteryType.NMC, capacityKwh, degradationPercent, 250.0, 11.0
        );
        
        
        double remainingCapacity = batteryProfile.getRemainingCapacityKwh();
        
        
        double expected = capacityKwh * (1 - degradationPercent / 100);
        assertEquals(expected, remainingCapacity, 0.001);
    }
    
    @Test
    public void testEqualsAndHashCode() {
        
        BatteryProfile profile1 = new BatteryProfile(
            BatteryType.NMC, 80.0, 5.0, 250.0, 11.0
        );
        BatteryProfile profile2 = new BatteryProfile(
            BatteryType.NMC, 80.0, 5.0, 250.0, 11.0
        );
        BatteryProfile profile3 = new BatteryProfile(
            BatteryType.LFP, 80.0, 5.0, 250.0, 11.0
        );
        
        
        assertEquals(profile1, profile2);
        assertEquals(profile1.hashCode(), profile2.hashCode());
        assertNotEquals(profile1, profile3);
        assertNotEquals(profile1.hashCode(), profile3.hashCode());
    }
    
    @Test
    public void testToString() {
        
        BatteryProfile profile = new BatteryProfile(
            BatteryType.NMC, 80.0, 5.0, 250.0, 11.0
        );
        
        
        String result = profile.toString();
        
        
        assertTrue(result.contains("NMC"));
        assertTrue(result.contains("Capacity"));
        assertTrue(result.contains("Degradation"));
        assertTrue(result.contains("Remaining"));
        assertTrue(result.contains("Max DC Power"));
        assertTrue(result.contains("Max AC Power"));
    }
    
    @Test
    public void testInvalidCapacity() {
        
        assertThrows(IllegalArgumentException.class, () -> {
            new BatteryProfile(BatteryType.NMC, 0.0, 5.0, 250.0, 11.0);
        });
        
        assertThrows(IllegalArgumentException.class, () -> {
            new BatteryProfile(BatteryType.NMC, -10.0, 5.0, 250.0, 11.0);
        });
    }
    
    @Test
    public void testInvalidDegradation() {
        
        assertThrows(IllegalArgumentException.class, () -> {
            new BatteryProfile(BatteryType.NMC, 80.0, -5.0, 250.0, 11.0);
        });
        
        assertThrows(IllegalArgumentException.class, () -> {
            new BatteryProfile(BatteryType.NMC, 80.0, 105.0, 250.0, 11.0);
        });
    }
    
    @Test
    public void testInvalidPower() {
        
        assertThrows(IllegalArgumentException.class, () -> {
            new BatteryProfile(BatteryType.NMC, 80.0, 5.0, 0.0, 11.0);
        });
        
        assertThrows(IllegalArgumentException.class, () -> {
            new BatteryProfile(BatteryType.NMC, 80.0, 5.0, 250.0, 0.0);
        });
    }
    
    @Test
    public void testNullType() {
        
        assertThrows(NullPointerException.class, () -> {
            new BatteryProfile(null, 80.0, 5.0, 250.0, 11.0);
        });
    }
} 