package com.core.system.core_system_back.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.core.system.core_system_back.model.Profile;

public interface ProfileRepository extends JpaRepository<Profile, UUID>{

}
