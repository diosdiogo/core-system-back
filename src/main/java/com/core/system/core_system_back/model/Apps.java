package com.core.system.core_system_back.model;

import java.util.UUID;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;


@Data
@Entity
public class Apps {
    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @Column(name = "nome", length = 200, nullable = false)
    private String name;

    @Column(nullable = false)
    private String url;

    @Column
    private String icon;

    @Column
    private String versao;

    @Column(name="ativo")
    private Boolean ativo;

    @Column(name="status")
    private String status;

}
