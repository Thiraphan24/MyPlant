package com.stou.example.thymeleaf.springapp3_myplant;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PlantService {
    @Autowired private PlantRepository plantRepository;

    public PlantService(PlantRepository plantRepository) {
        this.plantRepository = plantRepository;
    }

    public List<Plant>  getPlant() {
        return plantRepository.findAll();
    }

    public  Plant addPlant(Plant plant) {
        return plantRepository.save(plant);
    }


    public Optional<Plant> findPlantById(Integer id) {
        return plantRepository.findById(id);
    }

    public void save(Plant plant) {
        plantRepository.save(plant);
    }


    public List<Plant> listAll(String keyword) {
        if( keyword != null) {
            return plantRepository.findByThNameContainingIgnoreCaseOrEngNameContainingIgnoreCase(keyword, keyword);
        }
        return plantRepository.findAll();
    }

    public void deletePlant(Plant plant) {
        plantRepository.delete(plant);
    }
}
