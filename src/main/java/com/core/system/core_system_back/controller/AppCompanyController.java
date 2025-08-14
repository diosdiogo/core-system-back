package com.core.system.core_system_back.controller;

import java.util.List;
import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.core.system.core_system_back.dto.AppCompanyDTO;
import com.core.system.core_system_back.dto.AppComapnyResponseDTO;
import com.core.system.core_system_back.service.AppCompanyService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/app-company")
@Tag(name = "AppCompany", description = "Controle de relacionamentos entre aplicações e empresas")
public class AppCompanyController {

    private final AppCompanyService appCompanyService;

    public AppCompanyController(AppCompanyService appCompanyService) {
        this.appCompanyService = appCompanyService;
    }

    @PostMapping
    @Operation(summary = "Cadastrar relacionamento App-Empresa", 
               description = "Cria um novo relacionamento entre uma aplicação e uma empresa")
    public ResponseEntity<AppComapnyResponseDTO> createAppCompany(@Valid @RequestBody AppCompanyDTO dto) {
        AppComapnyResponseDTO created = appCompanyService.createAppCompany(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    @GetMapping
    @Operation(summary = "Listar todos os relacionamentos", 
               description = "Retorna todos os relacionamentos entre aplicações e empresas")
    public ResponseEntity<List<AppComapnyResponseDTO>> getAllAppCompanies() {
        List<AppComapnyResponseDTO> appCompany = appCompanyService.getAllAppCompanies();
        return ResponseEntity.ok(appCompany);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Buscar por ID", 
               description = "Retorna um relacionamento específico pelo ID")
    public ResponseEntity<AppComapnyResponseDTO> getAppCompanyById(@PathVariable UUID id) {
        AppComapnyResponseDTO appCompany = appCompanyService.getAppCompanyById(id);
        return ResponseEntity.ok(appCompany);
    }

    @GetMapping("/company/{companyId}")
    @Operation(summary = "Buscar por empresa", 
               description = "Retorna todos os relacionamentos de uma empresa específica")
    public ResponseEntity<List<AppComapnyResponseDTO>> getAppCompaniesByCompany(@PathVariable UUID companyId) {
        List<AppComapnyResponseDTO> appCompany = appCompanyService.getAppCompaniesByCompany(companyId);
        return ResponseEntity.ok(appCompany);
    }

    @GetMapping("/app/{appId}")
    @Operation(summary = "Buscar por aplicação", 
               description = "Retorna todos os relacionamentos de uma aplicação específica")
    public ResponseEntity<List<AppComapnyResponseDTO>> getAppCompaniesByApp(@PathVariable UUID appId) {
        List<AppComapnyResponseDTO> appCompany = appCompanyService.getAppCompaniesByApp(appId);
        return ResponseEntity.ok(appCompany);
    }

    @GetMapping("/company/{companyId}/app/{appId}")
    @Operation(summary = "Buscar por empresa e aplicação", 
               description = "Retorna o relacionamento específico entre uma empresa e uma aplicação")
    public ResponseEntity<AppComapnyResponseDTO> getAppCompanyByCompanyAndApp(
            @PathVariable UUID companyId, 
            @PathVariable UUID appId) {
        AppComapnyResponseDTO appCompany = appCompanyService.getAppCompanyByCompanyAndApp(companyId, appId);
        return ResponseEntity.ok(appCompany);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Atualizar relacionamento", 
               description = "Atualiza um relacionamento existente")
    public ResponseEntity<AppComapnyResponseDTO> updateAppCompany(
            @PathVariable UUID id, 
            @Valid @RequestBody AppCompanyDTO dto) {
        AppComapnyResponseDTO updated = appCompanyService.updateAppCompany(id, dto);
        return ResponseEntity.ok(updated);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Excluir relacionamento", 
               description = "Remove um relacionamento entre aplicação e empresa")
    public ResponseEntity<Void> deleteAppCompany(@PathVariable UUID id) {
        appCompanyService.deleteAppCompany(id);
        return ResponseEntity.noContent().build();
    }
} 