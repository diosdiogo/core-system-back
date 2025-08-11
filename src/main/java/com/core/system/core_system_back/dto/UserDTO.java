package com.core.system.core_system_back.dto;

import java.util.UUID;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class UserDTO {

    @NotBlank
    private String name;

    @Email
    @NotBlank
    private String email;

    @NotBlank
    private String userSystem;

    @NotBlank
    private String password;

    @NotBlank
    private String contato;

    @NotNull
    private Boolean active;

    // Endereço opcional - pode usar ID existente ou criar novo
    private UUID addressId; // ID do endereço existente (opcional)

    // Campos para criar novo endereço (opcional)
    private String street;
    private String number;
    private String neighborhood;
    private String complement;
    private String zipCode;
    
    // Opção 1: Usar nomes (mais amigável para o usuário)
    private String cityName;   // Nome da cidade
    private String stateName;  // Nome do estado
    private String countryName; // Nome do país
    
    // Opção 2: Usar IDs (mais preciso)
    private String cityId;     // ID da cidade
    private String stateId;    // ID do estado
    private String countryId;  // ID do país

    @NotNull
    private UUID profileId;

    @NotNull
    private UUID companyId;
}
