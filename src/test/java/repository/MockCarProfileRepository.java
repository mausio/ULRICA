package repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import model.CarProfile;


public class MockCarProfileRepository implements CarProfileRepository {
    private final Map<String, CarProfile> profiles;
    private List<String> operationLog;

    public MockCarProfileRepository() {
        this.profiles = new HashMap<>();
        this.operationLog = new ArrayList<>();
    }

    @Override
    public List<CarProfile> findAll() {
        operationLog.add("findAll called");
        return new ArrayList<>(profiles.values());
    }

    @Override
    public CarProfile findById(String id) {
        operationLog.add("findById called with id: " + id);
        return profiles.get(id);
    }

    @Override
    public void save(CarProfile carProfile) {
        operationLog.add("save called with profile id: " + carProfile.getId());
        profiles.put(carProfile.getId(), carProfile);
    }

    @Override
    public void update(CarProfile carProfile) {
        operationLog.add("update called with profile id: " + carProfile.getId());
        profiles.put(carProfile.getId(), carProfile);
    }

    @Override
    public void delete(String id) {
        operationLog.add("delete called with id: " + id);
        profiles.remove(id);
    }


    public void clearProfiles() {
        profiles.clear();
        operationLog.clear();
    }

    public List<String> getOperationLog() {
        return new ArrayList<>(operationLog);
    }

    public int getProfileCount() {
        return profiles.size();
    }
} 