package com.core.system.core_system_back.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.core.system.core_system_back.dto.AppCompanyDTO;
import com.core.system.core_system_back.dto.AppComapnyResponseDTO;
import com.core.system.core_system_back.exception.AppNotFoundException;
import com.core.system.core_system_back.exception.CompanyNotFoundException;
import com.core.system.core_system_back.exception.DuplicateAppCompanyException;
import com.core.system.core_system_back.model.AppCompany;
import com.core.system.core_system_back.model.Apps;
import com.core.system.core_system_back.model.Company;
import com.core.system.core_system_back.repository.AppCompanyRepository;
import com.core.system.core_system_back.repository.AppsRepository;
import com.core.system.core_system_back.repository.CompanyRepository;

@Service
public class AppCompanyService {

    private final AppCompanyRepository appCompanyRepository;
    private final AppsRepository appsRepository;
    private final CompanyRepository companyRepository;

    public AppCompanyService(AppCompanyRepository appCompanyRepository,
                            AppsRepository appsRepository,
                            CompanyRepository companyRepository) {
        this.appCompanyRepository = appCompanyRepository;
        this.appsRepository = appsRepository;
        this.companyRepository = companyRepository;
    }

    public AppComapnyResponseDTO createAppCompany(AppCompanyDTO dto) {
        // Verificar se a aplicação existe
        Apps app = appsRepository.findById(dto.getAppId())
                .orElseThrow(() -> new AppNotFoundException("Aplicação não encontrada com ID: " + dto.getAppId()));

        // Verificar se a empresa existe
        Company company = companyRepository.findById(dto.getCompanyId())
                .orElseThrow(() -> new CompanyNotFoundException("Empresa não encontrada com ID: " + dto.getCompanyId()));

        // Verificar se já existe um relacionamento para esta aplicação e empresa
        if (appCompanyRepository.existsByCompany_IdAndApp_Id(dto.getCompanyId(), dto.getAppId())) {
            throw new DuplicateAppCompanyException("Já existe um relacionamento entre esta aplicação e empresa");
        }

        // Criar o relacionamento
        AppCompany appCompany = new AppCompany();
        appCompany.setApp(app);
        appCompany.setCompany(company);
        appCompany.setAtivo(dto.getAtivo() != null ? dto.getAtivo() : true);
        appCompany.setStatus(dto.getStatus() != null ? dto.getStatus() : "ATIVO");
        appCompany.setValidade(dto.getValidade());
        

        AppCompany saved = appCompanyRepository.save(appCompany);
        return convertToResponseDTO(saved);
    }

    public List<AppComapnyResponseDTO> getAllAppCompanies() {
        List<AppCompany> appCompanies = appCompanyRepository.findAllWithRelations();
        return appCompanies.stream()
                .map(this::convertToResponseDTO)
                .collect(Collectors.toList());
    }

    public AppComapnyResponseDTO getAppCompanyById(UUID id) {
        AppCompany appCompany = appCompanyRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Relacionamento App-Empresa não encontrado com ID: " + id));
        return convertToResponseDTO(appCompany);
    }

    public List<AppComapnyResponseDTO> getAppCompaniesByCompany(UUID companyId) {
        List<AppCompany> appCompanies = appCompanyRepository.findByCompanyIdWithRelations(companyId);
        return appCompanies.stream()
                .map(this::convertToResponseDTO)
                .collect(Collectors.toList());
    }

    public List<AppComapnyResponseDTO> getAppCompaniesByApp(UUID appId) {
        List<AppCompany> appCompanies = appCompanyRepository.findByAppIdWithRelations(appId);
        return appCompanies.stream()
                .map(this::convertToResponseDTO)
                .collect(Collectors.toList());
    }

    public AppComapnyResponseDTO getAppCompanyByCompanyAndApp(UUID companyId, UUID appId) {
        AppCompany appCompany = appCompanyRepository.findByCompany_IdAndApp_Id(companyId, appId)
                .orElseThrow(() -> new RuntimeException("Relacionamento App-Empresa não encontrado para empresa: " + companyId + " e aplicação: " + appId));
        return convertToResponseDTO(appCompany);
    }

    public AppComapnyResponseDTO updateAppCompany(UUID id, AppCompanyDTO dto) {
        AppCompany appCompany = appCompanyRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Relacionamento App-Empresa não encontrado com ID: " + id));

        // Atualizar campos
        if (dto.getAtivo() != null) {
            appCompany.setAtivo(dto.getAtivo());
        }
        if (dto.getStatus() != null) {
            appCompany.setStatus(dto.getStatus());
        }
        if (dto.getValidade() != null) {
            appCompany.setValidade(dto.getValidade());
        }

        AppCompany saved = appCompanyRepository.save(appCompany);
        return convertToResponseDTO(saved);
    }

    public void deleteAppCompany(UUID id) {
        if (!appCompanyRepository.existsById(id)) {
            throw new RuntimeException("Relacionamento App-Empresa não encontrado com ID: " + id);
        }
        appCompanyRepository.deleteById(id);
    }

    private AppComapnyResponseDTO convertToResponseDTO(AppCompany appCompany) {
        return new AppComapnyResponseDTO(
            appCompany.getId(),
            appCompany.getApp().getId(),
            appCompany.getApp().getName(),
            appCompany.getCompany().getId(),
            appCompany.getCompany().getName_fant(),
            appCompany.getAtivo(),
            appCompany.getStatus(),
            appCompany.getValidade()
        );
    }
} 