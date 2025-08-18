package com.core.system.core_system_back.dto;

import lombok.Data;

@Data
public class SubMenuDTO {
    private String id;
    private String name;
    private String icon;
    private String url;
    private String versao;
    private Boolean ativo;
    private String status;
}
