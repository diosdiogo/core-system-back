package com.core.system.core_system_back.service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.core.system.core_system_back.dto.UserResponsibleDTO;
import com.core.system.core_system_back.dto.UserResponsibleResponseDTO;
import com.core.system.core_system_back.exception.UserNotFoundException;
import com.core.system.core_system_back.exception.CompanyNotFoundException;
import com.core.system.core_system_back.exception.DuplicateResponsibilityException;
import com.core.system.core_system_back.model.Company;
import com.core.system.core_system_back.model.User;
import com.core.system.core_system_back.model.UserResponsible;
import com.core.system.core_system_back.repository.CompanyRepository;
import com.core.system.core_system_back.repository.UserRepository;
import com.core.system.core_system_back.repository.UserResponsibleRepository;

import jakarta.persistence.EntityNotFoundException;

@Service
public class UserResponsibleService {

    private final UserResponsibleRepository userResponsibleRepository;
    private final UserRepository userRepository;
    private final CompanyRepository companyRepository;

    public UserResponsibleService(UserResponsibleRepository userResponsibleRepository,
                                UserRepository userRepository,
                                CompanyRepository companyRepository) {
        this.userResponsibleRepository = userResponsibleRepository;
        this.userRepository = userRepository;
        this.companyRepository = companyRepository;
    }

    public UserResponsibleResponseDTO createUserResponsible(UserResponsibleDTO dto) {
        // Verificar se o usuário existe
        User user = userRepository.findById(dto.getUserId())
                .orElseThrow(() -> new UserNotFoundException("Usuário não encontrado com ID: " + dto.getUserId()));

        // Verificar se a empresa existe
        Company company = companyRepository.findById(dto.getCompanyId())
                .orElseThrow(() -> new CompanyNotFoundException("Empresa não encontrada com ID: " + dto.getCompanyId()));

        // Verificar se já existe um vínculo para este usuário e empresa
        if (userResponsibleRepository.existsByUser_IdAndCompany_Id(dto.getUserId(), dto.getCompanyId())) {
            throw new DuplicateResponsibilityException("Usuário já é responsável por esta empresa");
        }

        // Criar o vínculo
        UserResponsible userResponsible = new UserResponsible();
        userResponsible.setUser(user);
        userResponsible.setCompany(company);
        userResponsible.setCargo(dto.getCargo());

        UserResponsible saved = userResponsibleRepository.save(userResponsible);
        return convertToResponseDTO(saved);
    }

    public List<UserResponsibleResponseDTO> getUserResponsibilities(UUID userId) {
        List<UserResponsible> responsibilities = userResponsibleRepository.findByUserIdWithRelations(userId);
        return responsibilities.stream()
                .map(this::convertToResponseDTO)
                .collect(Collectors.toList());
    }

    public List<UserResponsibleResponseDTO> getCompanyResponsibilities(UUID companyId) {
        List<UserResponsible> responsibilities = userResponsibleRepository.findByCompany_Id(companyId);
        return responsibilities.stream()
                .map(this::convertToResponseDTO)
                .collect(Collectors.toList());
    }

    public boolean isUserResponsibleForCompany(UUID userId, UUID companyId) {
        return userResponsibleRepository.existsByUser_IdAndCompany_Id(userId, companyId);
    }

    private UserResponsibleResponseDTO convertToResponseDTO(UserResponsible userResponsible) {
        return new UserResponsibleResponseDTO(
            userResponsible.getId(),
            userResponsible.getUser().getId(),
            userResponsible.getUser().getName(),
            userResponsible.getUser().getEmail(),
            userResponsible.getCompany().getId(),
            userResponsible.getCompany().getName_fant(),
            userResponsible.getCargo()
        );
    }
} 