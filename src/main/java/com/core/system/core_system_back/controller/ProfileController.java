package com.core.system.core_system_back.controller;

import com.core.system.core_system_back.dto.ProfileDTO;
import com.core.system.core_system_back.model.Profile;
import com.core.system.core_system_back.service.ProfileService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/profile")
@Tag(name = "Profile", description = "Controle de perfis")
public class ProfileController {

    private final ProfileService profileService;

    public ProfileController(ProfileService profileService) {
        this.profileService = profileService;
    }

    @PostMapping
    @Operation(summary = "Criar perfil", description = "Cadastrar novo perfil no sistema")
    public ResponseEntity<Profile> createProfile(@RequestBody ProfileDTO dto) {
        Profile profile = profileService.createProfile(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(profile);
    }
}
