package com.core.system.core_system_back.model;

import java.time.LocalDateTime;
import java.util.UUID;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "access_menu")
public class AccessMenu {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @ManyToOne
    @JoinColumn(name = "id_app_company", nullable = false)
    private AppCompany appCompany;

    @ManyToOne
    @JoinColumn(name = "id_menu", nullable = false)
    private Menu menu;

    @ManyToOne
    @JoinColumn(name = "id_perfil", nullable = false)
    private Profile profile;

    @Column(name = "create")
    private Boolean create;

    @Column(name = "update")
    private Boolean update;

    @Column(name = "delete")
    private Boolean delete;

    @Column(name = "export")
    private Boolean export;

    @Column(name = "imprimir")
    private Boolean imprimir;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;
} 