package com.core.system.core_system_back.service;

import com.core.system.core_system_back.dto.CompanyDTO;
import com.core.system.core_system_back.model.Address;
import com.core.system.core_system_back.model.City;
import com.core.system.core_system_back.model.Company;
import com.core.system.core_system_back.repository.AddressRepository;
import com.core.system.core_system_back.repository.CityRepository;
import com.core.system.core_system_back.repository.CompanyRepository;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

@Service
public class CompanyService {

    private final CompanyRepository companyRepository;
    private final AddressRepository addressRepository;
    private final CityRepository cityRepository;

    public CompanyService(CompanyRepository companyRepository,
                          AddressRepository addressRepository,
                          CityRepository cityRepository) {
        this.companyRepository = companyRepository;
        this.addressRepository = addressRepository;
        this.cityRepository = cityRepository;
    }

    @Transactional
    public Company createCompany(CompanyDTO dto) {
        
        // 1 - Verifica se cidade existe
        City city = cityRepository.findByName(dto.getCityName())
                .orElseGet(() -> {
                    City newCity = new City();
                    newCity.setName(dto.getCityName());
                    return cityRepository.save(newCity);
                });

        // 2 - Cria Address com a cidade
        Address address = new Address();
        address.setStreet(dto.getStreet());
        address.setNumber(dto.getNumber());
        address.setNeighborhood(dto.getNeighborhood());
        address.setZipCode(dto.getZipCode());
        address.setCity(city);

        addressRepository.save(address);

        // 3 - Cria Company com Address
        Company company = new Company();
        company.setName_fant(dto.getNameFant());
        company.setRazao_social(dto.getRazaoSocial());
        company.setIe(dto.getIe());
        company.setIm(dto.getIm());
        company.setCnpj(dto.getCnpj());
        company.setMatriz(dto.getMatriz());
        company.setSocial_media(dto.getSocialMedia());
        company.setAdress(address);

        return companyRepository.save(company);
    }
}
