package com.core.system.core_system_back.dto;

import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserResponsibleDTO {
    private UUID userId;
    private UUID companyId;
    private String cargo;
} 