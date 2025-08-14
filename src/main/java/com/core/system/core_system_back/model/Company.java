package com.core.system.core_system_back.model;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.core.system.core_system_back.enums.CompanyStatus;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.Data;

@Data
@Entity
public class Company {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @Column(nullable = false, length = 150)
    private String name_fant;
    
    @Column(nullable = false, length = 150)
    private String razao_social;

    @Column(nullable = false, length = 50)
    private String ie;

    @Column(nullable = false, length = 50)
    private String im;

    @Column(nullable = false, length = 150)
    private String cnpj;

    @Column(nullable = false, length = 150)
    private String matriz;

    @ManyToOne
    @JoinColumn(name = "address_id", nullable = false)
    private Address address;

    @Column(nullable = false, length = 150)
    private String social_media;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 50)
    private CompanyStatus status = CompanyStatus.ATIVO;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @OneToMany(mappedBy = "company")
    @JsonManagedReference
    private List<UserCompany> userCompanies;

    @OneToMany(mappedBy = "company")
    @JsonManagedReference
    private List<UserResponsible> userResponsibles;
}
