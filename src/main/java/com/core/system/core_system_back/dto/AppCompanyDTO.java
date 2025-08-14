package com.core.system.core_system_back.dto;

import java.time.LocalDate;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AppCompanyDTO {
    private UUID appId;
    private UUID companyId;
    private Boolean ativo;
    private String status;
    private LocalDate validade;
} 