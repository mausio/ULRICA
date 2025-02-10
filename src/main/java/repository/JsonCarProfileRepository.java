package repository;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import model.CarProfile;
import utils.generalUtils.GsonUtil;

import java.io.*;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class JsonCarProfileRepository implements CarProfileRepository {
    private static final String FILE_PATH = "src/main/resources/carProfiles.json";
    private final Gson gson;

    public JsonCarProfileRepository() {
        this.gson = new Gson();
        ensureFileExists();
    }

    private void ensureFileExists() {
        File file = new File(FILE_PATH);
        if (!file.exists()) {
            try {
                file.getParentFile().mkdirs();
                file.createNewFile();
                // Initialize with empty array
                try (FileWriter writer = new FileWriter(file)) {
                    writer.write("[]");
                }
            } catch (IOException e) {
                throw new RuntimeException("Failed to create car profiles file", e);
            }
        }
    }

    @Override
    public List<CarProfile> findAll() {
        try (Reader reader = new FileReader(FILE_PATH)) {
            Type listType = new TypeToken<ArrayList<CarProfile>>(){}.getType();
            List<CarProfile> profiles = gson.fromJson(reader, listType);
            return profiles != null ? profiles : new ArrayList<>();
        } catch (IOException e) {
            throw new RuntimeException("Failed to read car profiles", e);
        }
    }

    @Override
    public CarProfile findById(String id) {
        return findAll().stream()
                .filter(profile -> profile.getId().equals(id))
                .findFirst()
                .orElse(null);
    }

    @Override
    public void save(CarProfile carProfile) {
        List<CarProfile> profiles = findAll();
        profiles.add(carProfile);
        writeToFile(profiles);
    }

    @Override
    public void update(CarProfile carProfile) {
        List<CarProfile> profiles = findAll();
        for (int i = 0; i < profiles.size(); i++) {
            if (profiles.get(i).getId().equals(carProfile.getId())) {
                profiles.set(i, carProfile);
                writeToFile(profiles);
                return;
            }
        }
    }

    @Override
    public void delete(String id) {
        List<CarProfile> profiles = findAll();
        profiles.removeIf(profile -> profile.getId().equals(id));
        writeToFile(profiles);
    }

    private void writeToFile(List<CarProfile> profiles) {
        try (FileWriter writer = new FileWriter(FILE_PATH)) {
            String json = GsonUtil.objectToGsonString(profiles, gson);
            writer.write(json);
        } catch (IOException e) {
            throw new RuntimeException("Failed to write car profiles", e);
        }
    }
} 