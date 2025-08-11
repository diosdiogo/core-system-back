package com.core.system.core_system_back.controller;

import java.util.List;
import java.util.UUID;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.core.system.core_system_back.dto.UserResponsibleDTO;
import com.core.system.core_system_back.dto.UserResponsibleResponseDTO;
import com.core.system.core_system_back.service.UserResponsibleService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/api/user-responsible")
@Tag(name = "User", description = "Controle de usuários responsáveis das empresas")
public class UserResponsibleController {

    private final UserResponsibleService userResponsibleService;

    public UserResponsibleController(UserResponsibleService userResponsibleService) {
        this.userResponsibleService = userResponsibleService;
    }

    @PostMapping
    @Operation(summary = "Cadastrar vínculo usuário-responsável-empresa", 
               description = "Cria um vínculo entre usuário, cargo e empresa")
    public UserResponsibleResponseDTO createUserResponsible(@RequestBody UserResponsibleDTO dto) {
        return userResponsibleService.createUserResponsible(dto);
    }

    @GetMapping("/user/{userId}")
    @Operation(summary = "Buscar responsabilidades de um usuário", 
               description = "Retorna todas as responsabilidades de um usuário específico")
    public List<UserResponsibleResponseDTO> getUserResponsibilities(@PathVariable UUID userId) {
        return userResponsibleService.getUserResponsibilities(userId);
    }

    @GetMapping("/company/{companyId}")
    @Operation(summary = "Buscar responsabilidades de uma empresa", 
               description = "Retorna todos os usuários responsáveis de uma empresa específica")
    public List<UserResponsibleResponseDTO> getCompanyResponsibilities(@PathVariable UUID companyId) {
        return userResponsibleService.getCompanyResponsibilities(companyId);
    }

    @GetMapping("/check/{userId}/{companyId}")
    @Operation(summary = "Verificar se usuário é responsável por empresa", 
               description = "Verifica se um usuário é responsável por uma empresa específica")
    public boolean isUserResponsibleForCompany(@PathVariable UUID userId, @PathVariable UUID companyId) {
        return userResponsibleService.isUserResponsibleForCompany(userId, companyId);
    }
} 