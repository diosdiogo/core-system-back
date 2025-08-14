package com.core.system.core_system_back.service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.core.system.core_system_back.dto.AppsDTO;
import com.core.system.core_system_back.model.Apps;
import com.core.system.core_system_back.repository.AppsRepository;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;

@Service
public class AppsService {

    private final AppsRepository appsRepository;

    public AppsService(AppsRepository appsRepository) {
        this.appsRepository = appsRepository;
    }

    @Transactional
    public Apps createApps(AppsDTO appsDTO) {
        Apps apps = new Apps();
        apps.setName(appsDTO.getName());
        apps.setUrl(appsDTO.getUrl());

        return appsRepository.save(apps);
    }
    
    public List<Apps> getAllApps() {
        return appsRepository.findAll();
    }

    public Optional<Apps> getAppsById(UUID id) {
        return appsRepository.findById(id);
    }
}
