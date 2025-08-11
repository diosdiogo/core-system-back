package com.core.system.core_system_back.dto;

import lombok.Data;
import java.util.UUID;

@Data
public class ProfileDTO {
    private String nome;
    private Boolean status;
    private UUID companyId;
}
