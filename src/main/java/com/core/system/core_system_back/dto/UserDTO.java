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

    @NotNull
    private UUID addressId;

    @NotNull
    private UUID profileId;

    @NotNull
    private UUID companyId;
}
