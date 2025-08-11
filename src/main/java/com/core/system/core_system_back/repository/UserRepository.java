package com.core.system.core_system_back.repository;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.core.system.core_system_back.model.User;
import java.util.List;
import java.util.Optional;


public interface UserRepository extends JpaRepository<User, UUID> {
    Optional<User> findByName(String name);
    Optional<User> findByEmail(String email);
    boolean existsByEmail(String email);
    
    @Query("SELECT u FROM User u " +
           "LEFT JOIN FETCH u.profile " +
           "LEFT JOIN FETCH u.companies uc " +
           "LEFT JOIN FETCH uc.company " +
           "WHERE u.email = :email")
    Optional<User> findByEmailWithRelations(@Param("email") String email);
}
