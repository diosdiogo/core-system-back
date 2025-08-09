package com.core.system.core_system_back.service;

import com.core.system.core_system_back.dto.UserDTO;
import com.core.system.core_system_back.model.Address;
import com.core.system.core_system_back.model.Company;
import com.core.system.core_system_back.model.Profile;
import com.core.system.core_system_back.model.User;
import com.core.system.core_system_back.model.UserCompany;
import com.core.system.core_system_back.repository.AddressRepository;
import com.core.system.core_system_back.repository.CompanyRepository;
import com.core.system.core_system_back.repository.ProfileRepository;
import com.core.system.core_system_back.repository.UserRepository;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;

import java.util.ArrayList;
import java.util.List;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final AddressRepository addressRepository;
    private final ProfileRepository profileRepository;
    private final CompanyRepository companyRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository,
                       AddressRepository addressRepository,
                       ProfileRepository profileRepository,
                       CompanyRepository companyRepository) {
        this.userRepository = userRepository;
        this.addressRepository = addressRepository;
        this.profileRepository = profileRepository;
        this.companyRepository = companyRepository;
        this.passwordEncoder = new BCryptPasswordEncoder();
    }

    @Transactional
    public User createUser(UserDTO dto) {
        if (userRepository.existsByEmail(dto.getEmail())) {
            throw new IllegalArgumentException("E-mail já cadastrado.");
        }

        Address address = addressRepository.findById(dto.getAddressId())
                .orElseThrow(() -> new EntityNotFoundException("Endereço não encontrado"));

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
        // user.setCompanies(company);

        // Inicializa a lista de empresas
        List<UserCompany> companies = new ArrayList<>();
        // Cria o vínculo usuário-empresa
        UserCompany userCompany = new UserCompany();
        userCompany.setUser(user);
        userCompany.setCompany(company);
        companies.add(userCompany);
        user.setCompanies(companies);

        return userRepository.save(user);
    }
}
