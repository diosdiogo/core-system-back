package com.core.system.core_system_back.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import java.util.List;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.core.system.core_system_back.model.Apps;
import com.core.system.core_system_back.repository.AppsRepository;
import com.core.system.core_system_back.service.AppsService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/api/apps")
@Tag(name = "Apps", description = "Operações de exemplo")
public class AppsController {

    private final AppsService appsService;

    public AppsController(AppsService appsService) {
        this.appsService = appsService;
    }
    
    @Autowired
    private AppsRepository appsRepository;

    @GetMapping
    @Operation(summary = "Obter Apps", description = "Retorna todos os apps")
    public List<Apps> getAllApps(Pageable page) {
        List<Apps> apps = appsService.getAllApps();
        return apps;
    }

    @PostMapping
    @Operation(summary = "Gravar App", description = "Grava um novo app")
    public ResponseEntity<Apps> createApp(@RequestBody Apps app) {
        return ResponseEntity.status(HttpStatus.CREATED)
                             .body(appsRepository.save(app));
    }
    
}
