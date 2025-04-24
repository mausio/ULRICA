package org.ulrica.application.port.out;

import java.util.List;
import java.util.Optional;

import org.ulrica.domain.entity.CarProfile;

public interface CarProfilePersistencePortInterface {
    Optional<CarProfile> findById(String id);
    List<CarProfile> findAll();
    CarProfile save(CarProfile carProfile);
    void delete(String id);
} 