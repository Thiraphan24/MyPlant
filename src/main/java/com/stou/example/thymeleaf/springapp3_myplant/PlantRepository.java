package com.stou.example.thymeleaf.springapp3_myplant;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PlantRepository extends JpaRepository<Plant, Integer> {
    List<Plant> findByThNameContainingIgnoreCaseOrEngNameContainingIgnoreCase(String thName, String engName);
}

