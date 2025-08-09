package com.core.system.core_system_back.repository;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.core.system.core_system_back.model.User;
import java.util.List;
import java.util.Optional;


public interface UserRepository extends JpaRepository<User, UUID> {
    Optional<User> findByName(String name);
    
    boolean existsByEmail(String email);
}
