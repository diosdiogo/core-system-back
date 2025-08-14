package com.core.system.core_system_back.dto;

import lombok.Data;

@Data
public class AppsDTO {
    private String id;
    private String name;
    private String url;
    private String icon;
    private String versao;
    private Boolean ativo;
    private String status;
}
