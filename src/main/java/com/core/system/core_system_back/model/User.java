package com.core.system.core_system_back.model;

import java.util.UUID;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Data;

@Data
@Entity
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @Column(nullable = false, length = 100)
    private String name;

    @Column(nullable = false, length = 100)
    private String email;

    @Column(name = "user_system", nullable = false, length = 100)
    private String user;


    @Column(nullable = false, length = 200)
    private String password;

    @Column(nullable = false, length = 200)
    private String contato;

    @Column(nullable = false)
    private Boolean active;

    @ManyToOne
    @JoinColumn(name = "address_id", nullable = false)
    private Address adress;

    @ManyToOne
    @JoinColumn(name = "profile_id", nullable = false)
    private Profile profile;

    @ManyToOne
    @JoinColumn(name = "company_id", nullable = false)
    private Company company;
}
