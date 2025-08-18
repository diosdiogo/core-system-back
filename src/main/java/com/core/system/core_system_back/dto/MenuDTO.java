package com.core.system.core_system_back.dto;

import lombok.Data;

@Data
public class MenuDTO {
    private String id;
    private String name;
    private String icon;
    private String url;
    private String versao;
    private Boolean ativo;
    private String status;
}
