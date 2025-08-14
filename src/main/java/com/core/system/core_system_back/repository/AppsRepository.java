package com.core.system.core_system_back.repository;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.core.system.core_system_back.model.Apps;

@Repository
public interface AppsRepository extends JpaRepository<Apps, UUID> {

    Optional<Apps> findByName(String name);

    // public List<Apps> findAll(Pageable page);
    
}
