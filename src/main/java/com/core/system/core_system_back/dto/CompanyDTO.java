package com.core.system.core_system_back.dto;

import lombok.Data;

@Data
public class CompanyDTO {
    private String nameFant;
    private String razaoSocial;
    private String ie;
    private String im;
    private String cnpj;
    private String matriz;
    private String socialMedia;
    
    // Opção 1: Usar nomes (mais amigável para o usuário)
    private String cityName;   // Nome da cidade
    private String stateName;  // Nome do estado
    private String countryName; // Nome do país
    
    // Opção 2: Usar IDs (mais preciso)
    private String cityId;     // ID da cidade
    private String stateId;    // ID do estado
    private String countryId;  // ID do país
    
    private String street;
    private String number;
    private String neighborhood;
    private String complement; // Complemento do endereço
    private String zipCode;
}
