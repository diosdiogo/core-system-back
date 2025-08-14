package com.core.system.core_system_back.dto;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AppComapnyResponseDTO {
    private UUID id;
    private UUID appId;
    private String appName;
    private UUID companyId;
    private String companyName;
    private Boolean ativo;
    private String status;
    private LocalDate validade;
} 