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
public class Address {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @Column(name = "street", nullable = false, length = 100)
    private String street;

    @Column(name = "number", nullable = false, length = 100)
    private String number;

    @Column(name = "neighborhood", nullable = true, length = 100)
    private String neighborhood;

    @Column(name = "complement", nullable = true, length = 100)
    private String complement;

    @Column(name = "zip_code", nullable = false, length = 100)
    private String zipCode;

    @ManyToOne
    @JoinColumn(name = "city_id", nullable = false)
    private City city;

}
