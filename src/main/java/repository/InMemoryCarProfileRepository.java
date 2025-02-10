package repository;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import model.CarProfile;

public class InMemoryCarProfileRepository implements CarProfileRepository {
    private final List<CarProfile> profiles;

    public InMemoryCarProfileRepository() {
        // Using CopyOnWriteArrayList for thread safety
        this.profiles = new CopyOnWriteArrayList<>();
    }

    @Override
    public List<CarProfile> findAll() {
        return new ArrayList<>(profiles);
    }

    @Override
    public CarProfile findById(String id) {
        return profiles.stream()
                .filter(profile -> profile.getId().equals(id))
                .findFirst()
                .orElse(null);
    }

    @Override
    public void save(CarProfile carProfile) {
        profiles.add(carProfile);
    }

    @Override
    public void update(CarProfile carProfile) {
        for (int i = 0; i < profiles.size(); i++) {
            if (profiles.get(i).getId().equals(carProfile.getId())) {
                profiles.set(i, carProfile);
                return;
            }
        }
    }

    @Override
    public void delete(String id) {
        profiles.removeIf(profile -> profile.getId().equals(id));
    }

    // Additional method for testing
    public void clear() {
        profiles.clear();
    }
} 