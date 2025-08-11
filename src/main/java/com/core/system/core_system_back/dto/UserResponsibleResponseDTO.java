package com.core.system.core_system_back.dto;

import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserResponsibleResponseDTO {
    private UUID id;
    private UUID userId;
    private String userName;
    private String userEmail;
    private UUID companyId;
    private String companyName;
    private String cargo;
} 