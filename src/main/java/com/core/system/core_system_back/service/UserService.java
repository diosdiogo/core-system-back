package com.core.system.core_system_back.service;

import com.core.system.core_system_back.dto.UserDTO;
import com.core.system.core_system_back.dto.UserResponseDTO;
import com.core.system.core_system_back.model.Address;
import com.core.system.core_system_back.model.City;
import com.core.system.core_system_back.model.Company;
import com.core.system.core_system_back.model.Country;
import com.core.system.core_system_back.model.Profile;
import com.core.system.core_system_back.model.State;
import com.core.system.core_system_back.model.User;
import com.core.system.core_system_back.model.UserCompany;
import com.core.system.core_system_back.repository.AddressRepository;
import com.core.system.core_system_back.repository.CityRepository;
import com.core.system.core_system_back.repository.CompanyRepository;
import com.core.system.core_system_back.repository.CountryRepository;
import com.core.system.core_system_back.repository.ProfileRepository;
import com.core.system.core_system_back.repository.StateRepository;
import com.core.system.core_system_back.repository.UserRepository;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final AddressRepository addressRepository;
    private final ProfileRepository profileRepository;
    private final CompanyRepository companyRepository;
    private final CityRepository cityRepository;
    private final StateRepository stateRepository;
    private final CountryRepository countryRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository,
                       AddressRepository addressRepository,
                       ProfileRepository profileRepository,
                       CompanyRepository companyRepository,
                       CityRepository cityRepository,
                       StateRepository stateRepository,
                       CountryRepository countryRepository) {
        this.userRepository = userRepository;
        this.addressRepository = addressRepository;
        this.profileRepository = profileRepository;
        this.companyRepository = companyRepository;
        this.cityRepository = cityRepository;
        this.stateRepository = stateRepository;
        this.countryRepository = countryRepository;
        this.passwordEncoder = new BCryptPasswordEncoder();
    }

    @Transactional
    public UserResponseDTO createUser(UserDTO dto) {
        if (userRepository.existsByEmail(dto.getEmail())) {
            throw new IllegalArgumentException("E-mail já cadastrado.");
        }

        // Buscar ou criar endereço
        Address address = getOrCreateAddress(dto);

        Profile profile = profileRepository.findById(dto.getProfileId())
                .orElseThrow(() -> new EntityNotFoundException("Perfil não encontrado"));

        Company company = companyRepository.findById(dto.getCompanyId())
                .orElseThrow(() -> new EntityNotFoundException("Empresa não encontrada"));

        User user = new User();
        user.setName(dto.getName());
        user.setEmail(dto.getEmail());
        user.setUser(dto.getUserSystem());
        user.setPassword(passwordEncoder.encode(dto.getPassword()));
        user.setContato(dto.getContato());
        user.setActive(dto.getActive());
        user.setAdress(address);
        user.setProfile(profile);

        // Inicializa a lista de empresas
        List<UserCompany> companies = new ArrayList<>();
        // Cria o vínculo usuário-empresa
        UserCompany userCompany = new UserCompany();
        userCompany.setUser(user);
        userCompany.setCompany(company);
        companies.add(userCompany);
        user.setCompanies(companies);

        User savedUser = userRepository.save(user);
        
        // Converte para DTO de resposta
        return convertToResponseDTO(savedUser, company);
    }
    
    private UserResponseDTO convertToResponseDTO(User user, Company company) {
        UserResponseDTO response = new UserResponseDTO();
        response.setId(user.getId());
        response.setName(user.getName());
        response.setEmail(user.getEmail());
        response.setUserSystem(user.getUser());
        response.setContato(user.getContato());
        response.setActive(user.getActive());
        response.setProfileId(user.getProfile().getId());
        response.setCompanyId(company.getId());
        
        // Adiciona endereço se existir
        if (user.getAdress() != null) {
            UserResponseDTO.AddressResponseDTO addressResponse = new UserResponseDTO.AddressResponseDTO();
            Address address = user.getAdress();
            addressResponse.setId(address.getId());
            addressResponse.setStreet(address.getStreet());
            addressResponse.setNumber(address.getNumber());
            addressResponse.setNeighborhood(address.getNeighborhood());
            addressResponse.setComplement(address.getComplement());
            addressResponse.setZipCode(address.getZipCode());
            
            if (address.getCity() != null) {
                addressResponse.setCityName(address.getCity().getName());
                if (address.getCity().getState() != null) {
                    addressResponse.setStateName(address.getCity().getState().getName());
                    if (address.getCity().getState().getPais() != null) {
                        addressResponse.setCountryName(address.getCity().getState().getPais().getName());
                    }
                }
            }
            
            response.setAddress(addressResponse);
        }
        
        return response;
    }
    
    private Address getOrCreateAddress(UserDTO dto) {
        // Se forneceu ID do endereço, busca o existente
        if (dto.getAddressId() != null) {
            return addressRepository.findById(dto.getAddressId())
                    .orElseThrow(() -> new EntityNotFoundException("Endereço não encontrado com ID: " + dto.getAddressId()));
        }
        
        // Se não forneceu endereço, retorna null (usuário sem endereço)
        if (dto.getStreet() == null || dto.getStreet().trim().isEmpty()) {
            return null;
        }
        
        // Cria novo endereço
        return createNewAddress(dto);
    }
    
    private Address createNewAddress(UserDTO dto) {
        // 1 - Buscar ou criar País
        Country country = getOrCreateCountry(dto);
        
        // 2 - Buscar ou criar Estado
        State state = getOrCreateState(dto, country);
        
        // 3 - Buscar ou criar Cidade
        City city = getOrCreateCity(dto, state);

        // 4 - Cria Address
        Address address = new Address();
        address.setStreet(dto.getStreet());
        address.setNumber(dto.getNumber());
        address.setNeighborhood(dto.getNeighborhood());
        address.setComplement(dto.getComplement());
        address.setZipCode(dto.getZipCode());
        address.setCity(city);

        return addressRepository.save(address);
    }
    
    private Country getOrCreateCountry(UserDTO dto) {
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
            throw new IllegalArgumentException("É necessário fornecer countryId ou countryName para criar endereço");
        }
    }
    
    private State getOrCreateState(UserDTO dto, Country country) {
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
            throw new IllegalArgumentException("É necessário fornecer stateId ou stateName para criar endereço");
        }
    }
    
    private City getOrCreateCity(UserDTO dto, State state) {
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
            throw new IllegalArgumentException("É necessário fornecer cityId ou cityName para criar endereço");
        }
    }
}
