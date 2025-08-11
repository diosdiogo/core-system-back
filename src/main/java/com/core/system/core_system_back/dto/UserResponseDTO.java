package com.core.system.core_system_back.dto;

import java.util.UUID;
import lombok.Data;

@Data
public class UserResponseDTO {
    private UUID id;
    private String name;
    private String email;
    private String userSystem;
    private String contato;
    private Boolean active;
    private UUID profileId;
    private UUID companyId;
    
    // Endereço (se existir)
    private AddressResponseDTO address;
    
    @Data
    public static class AddressResponseDTO {
        private UUID id;
        private String street;
        private String number;
        private String neighborhood;
        private String complement;
        private String zipCode;
        private String cityName;
        private String stateName;
        private String countryName;
    }
} 