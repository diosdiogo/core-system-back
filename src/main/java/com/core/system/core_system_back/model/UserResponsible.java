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
public class UserResponsible {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @Column(nullable = false, length = 150)
    private String name;
    
    @Column(nullable = false)
    private String cargo;

    @Column(nullable = false, length = 50)
    private String contato;

    @ManyToOne
    @JoinColumn(name = "id", nullable = false)
    private Company company;
}
