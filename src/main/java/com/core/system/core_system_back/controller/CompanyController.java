package com.core.system.core_system_back.controller;

import com.core.system.core_system_back.dto.CompanyDTO;
import com.core.system.core_system_back.model.Company;
import com.core.system.core_system_back.service.CompanyService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/companies")
@Tag(name = "Company", description = "Controle de empresas")
public class CompanyController {

    private final CompanyService companyService;

    public CompanyController(CompanyService companyService) {
        this.companyService = companyService;
    }

    @PostMapping
    @Operation(summary = "Criar empresa", description = "Cadastrar nova empresa no sistema")
    public ResponseEntity<Company> createCompany(@Valid @RequestBody CompanyDTO dto) {
        Company createdCompany = companyService.createCompany(dto);
        return ResponseEntity.ok(createdCompany);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Buscar empresa por ID", description = "Retorna uma empresa com base no ID fornecido")
    public ResponseEntity<Company> getCompanyById(@PathVariable UUID id) {
        Optional<Company> company = companyService.getCompanyById(id);
        return company.map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    @GetMapping
    @Operation(summary = "Listar todas as empresas", description = "Retorna uma lista de todas as empresas cadastradas")
    public ResponseEntity<List<Company>> getAllCompanies() {
        List<Company> companies = companyService.getAllCompanies();
        return ResponseEntity.ok(companies);
    }
} 