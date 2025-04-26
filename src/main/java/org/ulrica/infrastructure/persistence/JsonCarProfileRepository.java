package org.ulrica.infrastructure.persistence;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Type;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.ulrica.application.port.out.CarProfilePersistencePortInterface;
import org.ulrica.domain.entity.CarProfile;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

public class JsonCarProfileRepository implements CarProfilePersistencePortInterface {
    private static final String STORAGE_DIR = "data";
    private static final String FILE_NAME = "car_profiles.json";
    private final Gson gson;
    private final Path filePath;

    public JsonCarProfileRepository() {
        this(STORAGE_DIR, FILE_NAME);
    }
    
    protected JsonCarProfileRepository(String storageDir, String fileName) {
        this.gson = new GsonBuilder()
                .setPrettyPrinting()
                .registerTypeAdapterFactory(new OptionalTypeAdapterFactory())
                .create();
        this.filePath = Paths.get(storageDir, fileName);
        ensureStorageDirectoryExists();
    }

    private void ensureStorageDirectoryExists() {
        try {
            Files.createDirectories(filePath.getParent());
            if (!Files.exists(filePath)) {
                Files.createFile(filePath);
                saveProfiles(new ArrayList<>());
            }
        } catch (IOException e) {
            throw new RuntimeException("Failed to initialize storage", e);
        }
    }

    @Override
    public Optional<CarProfile> findById(String id) {
        return findAll().stream()
                .filter(profile -> profile.getId().equals(id))
                .findFirst();
    }

    @Override
    public List<CarProfile> findAll() {
        try (FileReader reader = new FileReader(filePath.toFile())) {
            Type listType = new TypeToken<List<CarProfile>>() {}.getType();
            List<CarProfile> profiles = gson.fromJson(reader, listType);
            return profiles != null ? profiles : new ArrayList<>();
        } catch (IOException e) {
            throw new RuntimeException("Failed to read car profiles", e);
        }
    }

    @Override
    public CarProfile save(CarProfile carProfile) {
        List<CarProfile> profiles = findAll();
        
        
        boolean found = false;
        for (int i = 0; i < profiles.size(); i++) {
            if (profiles.get(i).getId().equals(carProfile.getId())) {
                profiles.set(i, carProfile);
                found = true;
                break;
            }
        }
        
        if (!found) {
            profiles.add(carProfile);
        }
        
        saveProfiles(profiles);
        return carProfile;
    }

    @Override
    public void delete(String id) {
        List<CarProfile> profiles = findAll();
        profiles.removeIf(profile -> profile.getId().equals(id));
        saveProfiles(profiles);
    }

    private void saveProfiles(List<CarProfile> profiles) {
        try (FileWriter writer = new FileWriter(filePath.toFile())) {
            gson.toJson(profiles, writer);
        } catch (IOException e) {
            throw new RuntimeException("Failed to save car profiles", e);
        }
    }
} 