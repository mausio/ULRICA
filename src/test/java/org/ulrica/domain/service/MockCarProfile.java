package org.ulrica.domain.service;

import java.util.Optional;

import org.ulrica.domain.valueobject.BatteryProfile;
import org.ulrica.domain.valueobject.BatteryType;
import org.ulrica.domain.valueobject.ChargingCurve;
import org.ulrica.domain.valueobject.ConsumptionProfile;


public class MockCarProfile {
    private final BatteryProfile batteryProfile;
    private final Optional<ChargingCurve> chargingCurve;
    private final String id;
    private final String name;
    private final double maxDcPowerKw;
    private final double maxAcPowerKw;
    private final ConsumptionProfile consumptionProfile;

    private MockCarProfile(BatteryProfile batteryProfile, ChargingCurve chargingCurve,
                          String id, String name, double maxDcPowerKw, double maxAcPowerKw) {
        this.batteryProfile = batteryProfile;
        this.chargingCurve = Optional.ofNullable(chargingCurve);
        this.id = id;
        this.name = name;
        this.maxDcPowerKw = maxDcPowerKw;
        this.maxAcPowerKw = maxAcPowerKw;
        this.consumptionProfile = new ConsumptionProfile(15, 20, 25);
    }


    public static MockCarProfile createBasic() {
        BatteryProfile batteryProfile = new BatteryProfile(
            BatteryType.NMC,
            80.0,
            5.0,
            250.0,
            11.0
        );
        
        return new MockCarProfile(batteryProfile, null, "mock-id", "Mock EV", 250.0, 11.0);
    }

    public BatteryProfile getBatteryProfile() {
        return this.batteryProfile;
    }

    public Optional<ChargingCurve> getChargingCurve() {
        return this.chargingCurve;
    }

    public String getId() {
        return this.id;
    }

    public String getName() {
        return this.name;
    }

    public double getMaxDcPowerKw() {
        return this.maxDcPowerKw;
    }
    
    public double getMaxAcPowerKw() {
        return this.maxAcPowerKw;
    }
    
    public ConsumptionProfile getConsumptionProfile() {
        return this.consumptionProfile;
    }
} 