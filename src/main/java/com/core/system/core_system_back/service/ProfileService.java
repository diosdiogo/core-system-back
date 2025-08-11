package com.core.system.core_system_back.service;

import com.core.system.core_system_back.dto.ProfileDTO;
import com.core.system.core_system_back.model.Company;
import com.core.system.core_system_back.model.Profile;
import com.core.system.core_system_back.repository.CompanyRepository;
import com.core.system.core_system_back.repository.ProfileRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

@Service
public class ProfileService {

    private final ProfileRepository profileRepository;
    private final CompanyRepository companyRepository;

    public ProfileService(ProfileRepository profileRepository, CompanyRepository companyRepository) {
        this.profileRepository = profileRepository;
        this.companyRepository = companyRepository;
    }

    @Transactional
    public Profile createProfile(ProfileDTO dto) {
        Company company = companyRepository.findById(dto.getCompanyId())
                .orElseThrow(() -> new EntityNotFoundException("Empresa não encontrada"));

        Profile profile = new Profile();
        profile.setNome(dto.getNome());
        profile.setStatus(dto.getStatus());
        profile.setCompany(company);

        return profileRepository.save(profile);
    }
}
