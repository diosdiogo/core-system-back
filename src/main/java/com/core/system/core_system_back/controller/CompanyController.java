package com.core.system.core_system_back.controller;

import com.core.system.core_system_back.dto.CompanyDTO;
import com.core.system.core_system_back.model.Company;
import com.core.system.core_system_back.service.CompanyService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
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
} 