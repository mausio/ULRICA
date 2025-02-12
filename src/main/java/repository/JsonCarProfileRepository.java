package repository;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Reader;
import java.io.Writer;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import exception.LoadingException;
import model.CarProfile;
import model.strategy.RangeCalculationStrategy;
import utils.gson.RangeCalculationStrategyAdapter;

public class JsonCarProfileRepository implements CarProfileRepository {
    private static final String FILE_PATH = "car_profiles.json";
    private final Gson gson;

    public JsonCarProfileRepository() {
        this.gson = new GsonBuilder()
                .registerTypeAdapter(RangeCalculationStrategy.class, new RangeCalculationStrategyAdapter())
                .setPrettyPrinting()
                .create();
    }

    @Override
    public List<CarProfile> findAll() throws LoadingException {
        File file = new File(FILE_PATH);
        if (!file.exists()) {
            return new ArrayList<>();
        }

        try (Reader reader = new FileReader(file)) {
            Type listType = new TypeToken<ArrayList<CarProfile>>(){}.getType();
            List<CarProfile> profiles = gson.fromJson(reader, listType);
            return profiles != null ? profiles : new ArrayList<>();
        } catch (IOException e) {
            throw new LoadingException("Error loading car profiles: " + e.getMessage());
        }
    }

    @Override
    public CarProfile findById(String id) throws LoadingException {
        return findAll().stream()
                .filter(profile -> profile.getId().equals(id))
                .findFirst()
                .orElse(null);
    }

    @Override
    public void save(CarProfile profile) throws LoadingException {
        List<CarProfile> profiles = findAll();
        profiles.add(profile);
        saveAll(profiles);
    }

    @Override
    public void update(CarProfile profile) throws LoadingException {
        List<CarProfile> profiles = findAll();
        for (int i = 0; i < profiles.size(); i++) {
            if (profiles.get(i).getId().equals(profile.getId())) {
                profiles.set(i, profile);
                break;
            }
        }
        saveAll(profiles);
    }

    @Override
    public void delete(String id) throws LoadingException {
        List<CarProfile> profiles = findAll();
        profiles.removeIf(profile -> profile.getId().equals(id));
        saveAll(profiles);
    }

    private void saveAll(List<CarProfile> profiles) throws LoadingException {
        try (Writer writer = new FileWriter(FILE_PATH)) {
            gson.toJson(profiles, writer);
        } catch (IOException e) {
            throw new LoadingException("Error saving car profiles: " + e.getMessage());
        }
    }
} 