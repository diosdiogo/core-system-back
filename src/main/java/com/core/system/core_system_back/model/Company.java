package com.core.system.core_system_back.model;

import java.util.List;
import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
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
    private Address adress;

    @Column(nullable = false, length = 150)
    private String social_media;

    @OneToMany(mappedBy = "company")
    @JsonManagedReference
    private List<UserCompany> users;

}
