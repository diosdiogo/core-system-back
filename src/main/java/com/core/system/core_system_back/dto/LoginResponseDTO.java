package com.core.system.core_system_back.dto;

import java.util.List;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LoginResponseDTO {
    private String token;
    private String name;
    private String email;
    private String user;
    private String contato;
    private ProfileInfo profile;
    private List<CompanyInfo> companies;
    
    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class ProfileInfo {
        private UUID id;
        private String nome;
        private Boolean status;
    }
    
    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class CompanyInfo {
        private UUID id;
        private String name_fant;
        private String razao_social;
        private String cnpj;
        private Boolean isResponsible;
        private String cargo;
    }
}
