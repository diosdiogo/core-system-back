package com.core.system.core_system_back.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.core.system.core_system_back.model.Company;

public interface CompanyRepository extends JpaRepository<Company, UUID>{

}
