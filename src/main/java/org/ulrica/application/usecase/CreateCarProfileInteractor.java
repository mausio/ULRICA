package org.ulrica.application.usecase;

import java.util.UUID;

import org.ulrica.application.port.in.CreateCarProfileUseCaseInterface;
import org.ulrica.application.port.out.CarProfilePersistencePortInterface;
import org.ulrica.domain.entity.CarProfile;
import org.ulrica.domain.valueobject.BatteryProfile;
import org.ulrica.domain.valueobject.BatteryType;
import org.ulrica.domain.valueobject.ConsumptionProfile;

public class CreateCarProfileInteractor implements CreateCarProfileUseCaseInterface {
    private final CarProfilePersistencePortInterface persistencePort;

    public CreateCarProfileInteractor(CarProfilePersistencePortInterface persistencePort) {
        this.persistencePort = persistencePort;
    }

    @Override
    public CarProfile createCarProfile(CreateCarProfileCommand command) {
        BatteryProfile batteryProfile = new BatteryProfile(
                BatteryType.valueOf(command.getBatteryType()),
                command.getBatteryCapacityKwh(),
                command.getBatteryDegradationPercent()
        );

        ConsumptionProfile consumptionProfile = new ConsumptionProfile(
                command.getConsumptionAt50Kmh(),
                command.getConsumptionAt100Kmh(),
                command.getConsumptionAt130Kmh()
        );

        CarProfile carProfile = new CarProfile.Builder()
                .id(UUID.randomUUID().toString())
                .name(command.getName())
                .manufacturer(command.getManufacturer())
                .model(command.getModel())
                .year(command.getYear())
                .hasHeatPump(command.hasHeatPump())
                .wltpRangeKm(command.getWltpRangeKm())
                .maxDcPowerKw(command.getMaxDcPowerKw())
                .maxAcPowerKw(command.getMaxAcPowerKw())
                .batteryProfile(batteryProfile)
                .consumptionProfile(consumptionProfile)
                .build();

        return persistencePort.save(carProfile);
    }
} 