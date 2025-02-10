package repository;

import model.CarProfile;
import java.util.List;

public interface CarProfileRepository {
    List<CarProfile> findAll();
    CarProfile findById(String id);
    void save(CarProfile carProfile);
    void update(CarProfile carProfile);
    void delete(String id);
} 