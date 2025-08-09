package com.core.system.core_system_back.repository;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.core.system.core_system_back.model.City;

public interface CityRepository extends JpaRepository<City, UUID>{
    Optional<City> findByName(String name);
}
