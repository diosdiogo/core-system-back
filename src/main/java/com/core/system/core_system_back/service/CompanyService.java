package com.core.system.core_system_back.service;

import com.core.system.core_system_back.dto.CompanyDTO;
import com.core.system.core_system_back.model.Address;
import com.core.system.core_system_back.model.City;
import com.core.system.core_system_back.model.Company;
import com.core.system.core_system_back.model.Country;
import com.core.system.core_system_back.model.State;
import com.core.system.core_system_back.repository.AddressRepository;
import com.core.system.core_system_back.repository.CityRepository;
import com.core.system.core_system_back.repository.CompanyRepository;
import com.core.system.core_system_back.repository.CountryRepository;
import com.core.system.core_system_back.repository.StateRepository;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class CompanyService {

    private final CompanyRepository companyRepository;
    private final AddressRepository addressRepository;
    private final CityRepository cityRepository;
    private final StateRepository stateRepository;
    private final CountryRepository countryRepository;

    public CompanyService(CompanyRepository companyRepository,
                          AddressRepository addressRepository,
                          CityRepository cityRepository,
                          StateRepository stateRepository,
                          CountryRepository countryRepository) {
        this.companyRepository = companyRepository;
        this.addressRepository = addressRepository;
        this.cityRepository = cityRepository;
        this.stateRepository = stateRepository;
        this.countryRepository = countryRepository;
    }

    @Transactional
    public Company createCompany(CompanyDTO dto) {
        
        // 1 - Buscar ou criar País
        Country country = getOrCreateCountry(dto);
        
        // 2 - Buscar ou criar Estado
        State state = getOrCreateState(dto, country);
        
        // 3 - Buscar ou criar Cidade
        City city = getOrCreateCity(dto, state);

        // 4 - Cria Address com a cidade
        Address address = new Address();
        address.setStreet(dto.getStreet());
        address.setNumber(dto.getNumber());
        address.setNeighborhood(dto.getNeighborhood()); // Neighborhood pode ser nulo
        address.setComplement(dto.getComplement()); // Complemento pode ser nulo
        address.setZipCode(dto.getZipCode());
        address.setCity(city);

        addressRepository.save(address);

        // 5 - Cria Company com Address
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
    
    private Country getOrCreateCountry(CompanyDTO dto) {
        if (dto.getCountryId() != null && !dto.getCountryId().isEmpty()) {
            return countryRepository.findById(UUID.fromString(dto.getCountryId()))
                    .orElseThrow(() -> new EntityNotFoundException("País não encontrado com ID: " + dto.getCountryId()));
        } else if (dto.getCountryName() != null && !dto.getCountryName().isEmpty()) {
            return countryRepository.findByName(dto.getCountryName())
                    .orElseGet(() -> {
                        Country newCountry = new Country();
                        newCountry.setName(dto.getCountryName());
                        return countryRepository.save(newCountry);
                    });
        } else {
            throw new IllegalArgumentException("É necessário fornecer countryId ou countryName");
        }
    }
    
    private State getOrCreateState(CompanyDTO dto, Country country) {
        if (dto.getStateId() != null && !dto.getStateId().isEmpty()) {
            return stateRepository.findById(UUID.fromString(dto.getStateId()))
                    .orElseThrow(() -> new EntityNotFoundException("Estado não encontrado com ID: " + dto.getStateId()));
        } else if (dto.getStateName() != null && !dto.getStateName().isEmpty()) {
            return stateRepository.findByName(dto.getStateName())
                    .orElseGet(() -> {
                        State newState = new State();
                        newState.setName(dto.getStateName());
                        newState.setPais(country);
                        return stateRepository.save(newState);
                    });
        } else {
            throw new IllegalArgumentException("É necessário fornecer stateId ou stateName");
        }
    }
    
    private City getOrCreateCity(CompanyDTO dto, State state) {
        if (dto.getCityId() != null && !dto.getCityId().isEmpty()) {
            return cityRepository.findById(UUID.fromString(dto.getCityId()))
                    .orElseThrow(() -> new EntityNotFoundException("Cidade não encontrada com ID: " + dto.getCityId()));
        } else if (dto.getCityName() != null && !dto.getCityName().isEmpty()) {
            return cityRepository.findByName(dto.getCityName())
                    .orElseGet(() -> {
                        City newCity = new City();
                        newCity.setName(dto.getCityName());
                        newCity.setState(state);
                        return cityRepository.save(newCity);
                    });
        } else {
            throw new IllegalArgumentException("É necessário fornecer cityId ou cityName");
        }
    }
}
