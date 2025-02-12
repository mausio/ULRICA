package repository;

import java.util.List;

import exception.LoadingException;
import model.CarProfile;

public interface CarProfileRepository {
    List<CarProfile> findAll() throws LoadingException;
    CarProfile findById(String id) throws LoadingException;
    void save(CarProfile carProfile) throws LoadingException;
    void update(CarProfile carProfile) throws LoadingException;
    void delete(String id) throws LoadingException;
} 