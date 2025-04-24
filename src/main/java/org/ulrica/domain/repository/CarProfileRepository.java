package org.ulrica.domain.repository;

import java.util.List;
import java.util.Optional;

import org.ulrica.domain.entity.CarProfile;

public interface CarProfileRepository {
    Optional<CarProfile> findById(String id);
    CarProfile findByName(String name);
    List<CarProfile> findAll();
    CarProfile save(CarProfile profile);
    void delete(String id);
} 