package org.ulrica.domain.entity;

import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThrows;
import static org.junit.Assert.assertTrue;
import org.junit.Before;
import org.junit.Test;
import org.ulrica.domain.valueobject.BatteryProfile;
import org.ulrica.domain.valueobject.BatteryType;
import org.ulrica.domain.valueobject.ChargingCurve;
import org.ulrica.domain.valueobject.ConsumptionProfile;

public class CarProfileTest {

    private BatteryProfile batteryProfile;
    private ConsumptionProfile consumptionProfile;
    private ChargingCurve chargingCurve;

    @Before
    public void setUp() {
        
        batteryProfile = new BatteryProfile(
            BatteryType.NMC,
            80.0,
            5.0,
            250.0,
            11.0
        );
        
        consumptionProfile = new ConsumptionProfile(15.0, 20.0, 25.0);
        
        Map<Double, Double> curvePoints = new HashMap<>();
        curvePoints.put(0.0, 150.0);
        curvePoints.put(50.0, 100.0);
        curvePoints.put(100.0, 10.0);
        chargingCurve = new ChargingCurve(curvePoints);
    }

    @Test
    public void testBuilderAndGetters() {
        
        String id = "test-id";
        String name = "Test EV";
        String manufacturer = "Test Manufacturer";
        String model = "Test Model";
        int year = 2023;
        boolean hasHeatPump = true;
        double wltpRangeKm = 500.0;
        double maxDcPowerKw = 250.0;
        double maxAcPowerKw = 11.0;
        
        
        CarProfile carProfile = new CarProfile.Builder()
            .id(id)
            .name(name)
            .manufacturer(manufacturer)
            .model(model)
            .year(year)
            .hasHeatPump(hasHeatPump)
            .wltpRangeKm(wltpRangeKm)
            .maxDcPowerKw(maxDcPowerKw)
            .maxAcPowerKw(maxAcPowerKw)
            .batteryProfile(batteryProfile)
            .consumptionProfile(consumptionProfile)
            .chargingCurve(chargingCurve)
            .build();
        
        
        assertEquals(id, carProfile.getId());
        assertEquals(name, carProfile.getName());
        assertEquals(manufacturer, carProfile.getManufacturer());
        assertEquals(model, carProfile.getModel());
        assertEquals(year, carProfile.getYear());
        assertEquals(hasHeatPump, carProfile.hasHeatPump());
        assertEquals(wltpRangeKm, carProfile.getWltpRangeKm(), 0.001);
        assertEquals(maxDcPowerKw, carProfile.getMaxDcPowerKw(), 0.001);
        assertEquals(maxAcPowerKw, carProfile.getMaxAcPowerKw(), 0.001);
        assertEquals(batteryProfile, carProfile.getBatteryProfile());
        assertEquals(consumptionProfile, carProfile.getConsumptionProfile());
        assertTrue(carProfile.getChargingCurve().isPresent());
        assertEquals(chargingCurve, carProfile.getChargingCurve().get());
    }
    
    @Test
    public void testBuilderWithoutOptionals() {
        
        CarProfile carProfile = new CarProfile.Builder()
            .name("Test EV")
            .manufacturer("Test Manufacturer")
            .model("Test Model")
            .year(2023)
            .hasHeatPump(true)
            .wltpRangeKm(500.0)
            .maxDcPowerKw(250.0)
            .maxAcPowerKw(11.0)
            .batteryProfile(batteryProfile)
            .consumptionProfile(consumptionProfile)
            .build();
        
        
        assertNotNull(carProfile.getId()); 
        assertFalse(carProfile.getChargingCurve().isPresent()); 
    }
    
    @Test
    public void testEqualsAndHashCode() {
        
        CarProfile profile1 = new CarProfile.Builder()
            .id("test-id")
            .name("Test EV")
            .manufacturer("Test Manufacturer")
            .model("Test Model")
            .year(2023)
            .hasHeatPump(true)
            .wltpRangeKm(500.0)
            .maxDcPowerKw(250.0)
            .maxAcPowerKw(11.0)
            .batteryProfile(batteryProfile)
            .consumptionProfile(consumptionProfile)
            .build();
        
        CarProfile profile2 = new CarProfile.Builder()
            .id("test-id") 
            .name("Test EV")
            .manufacturer("Test Manufacturer")
            .model("Test Model")
            .year(2023)
            .hasHeatPump(true)
            .wltpRangeKm(500.0)
            .maxDcPowerKw(250.0)
            .maxAcPowerKw(11.0)
            .batteryProfile(batteryProfile)
            .consumptionProfile(consumptionProfile)
            .build();
        
        CarProfile profile3 = new CarProfile.Builder()
            .id("different-id") 
            .name("Test EV")
            .manufacturer("Test Manufacturer")
            .model("Test Model")
            .year(2023)
            .hasHeatPump(true)
            .wltpRangeKm(500.0)
            .maxDcPowerKw(250.0)
            .maxAcPowerKw(11.0)
            .batteryProfile(batteryProfile)
            .consumptionProfile(consumptionProfile)
            .build();
        
        
        assertEquals(profile1, profile2);
        assertEquals(profile1.hashCode(), profile2.hashCode());
        assertNotEquals(profile1, profile3);
        assertNotEquals(profile1.hashCode(), profile3.hashCode());
    }
    
    @Test
    public void testToString() {
        
        CarProfile carProfile = new CarProfile.Builder()
            .id("test-id")
            .name("Test EV")
            .manufacturer("Test Manufacturer")
            .model("Test Model")
            .year(2023)
            .hasHeatPump(true)
            .wltpRangeKm(500.0)
            .maxDcPowerKw(250.0)
            .maxAcPowerKw(11.0)
            .batteryProfile(batteryProfile)
            .consumptionProfile(consumptionProfile)
            .build();
        
        
        String result = carProfile.toString();
        
        
        assertTrue(result.contains("Test EV"));
        assertTrue(result.contains("Test Manufacturer"));
        assertTrue(result.contains("Test Model"));
        assertTrue(result.contains("2023"));
        assertTrue(result.contains("Heat Pump: Yes"));
        assertTrue(result.contains("500.0 km"));
        assertTrue(result.contains("250.0 kW"));
        assertTrue(result.contains("11.0 kW"));
    }
    
    @Test
    public void testRequiredParameters() {
        
        assertThrows(NullPointerException.class, () -> {
            new CarProfile.Builder()
                .manufacturer("Test Manufacturer")
                .model("Test Model")
                .year(2023)
                .hasHeatPump(true)
                .wltpRangeKm(500.0)
                .maxDcPowerKw(250.0)
                .maxAcPowerKw(11.0)
                .batteryProfile(batteryProfile)
                .consumptionProfile(consumptionProfile)
                .build();
        });
        
        
        assertThrows(NullPointerException.class, () -> {
            new CarProfile.Builder()
                .name("Test EV")
                .model("Test Model")
                .year(2023)
                .hasHeatPump(true)
                .wltpRangeKm(500.0)
                .maxDcPowerKw(250.0)
                .maxAcPowerKw(11.0)
                .batteryProfile(batteryProfile)
                .consumptionProfile(consumptionProfile)
                .build();
        });
        
        
        assertThrows(NullPointerException.class, () -> {
            new CarProfile.Builder()
                .name("Test EV")
                .manufacturer("Test Manufacturer")
                .year(2023)
                .hasHeatPump(true)
                .wltpRangeKm(500.0)
                .maxDcPowerKw(250.0)
                .maxAcPowerKw(11.0)
                .batteryProfile(batteryProfile)
                .consumptionProfile(consumptionProfile)
                .build();
        });
        
        
        assertThrows(NullPointerException.class, () -> {
            new CarProfile.Builder()
                .name("Test EV")
                .manufacturer("Test Manufacturer")
                .model("Test Model")
                .year(2023)
                .hasHeatPump(true)
                .wltpRangeKm(500.0)
                .maxDcPowerKw(250.0)
                .maxAcPowerKw(11.0)
                .consumptionProfile(consumptionProfile)
                .build();
        });
        
        
        assertThrows(NullPointerException.class, () -> {
            new CarProfile.Builder()
                .name("Test EV")
                .manufacturer("Test Manufacturer")
                .model("Test Model")
                .year(2023)
                .hasHeatPump(true)
                .wltpRangeKm(500.0)
                .maxDcPowerKw(250.0)
                .maxAcPowerKw(11.0)
                .batteryProfile(batteryProfile)
                .build();
        });
    }
} 