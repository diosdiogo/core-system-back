package com.core.system.core_system_back.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.core.system.core_system_back.dto.LoginRequestDTO;
import com.core.system.core_system_back.dto.LoginResponseDTO;
import com.core.system.core_system_back.exception.InvalidPasswordException;
import com.core.system.core_system_back.exception.UserNotFoundException;
import com.core.system.core_system_back.model.User;
import com.core.system.core_system_back.model.UserCompany;
import com.core.system.core_system_back.repository.UserRepository;
import com.core.system.core_system_back.repository.UserResponsibleRepository;
import com.core.system.core_system_back.security.JwtUtil;


@Service
public class AuthService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;
    private final UserResponsibleRepository userResponsibleRepository;

    public AuthService(UserRepository userRepository, PasswordEncoder passwordEncoder, JwtUtil jwtUtil, UserResponsibleRepository userResponsibleRepository) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtUtil = jwtUtil;
        this.userResponsibleRepository = userResponsibleRepository;
    }

    public LoginResponseDTO login(LoginRequestDTO loginRequest) {
        User user = userRepository.findByEmailWithRelations(loginRequest.getEmail())
                .orElseThrow(() -> new UserNotFoundException("Usuário não encontrado com o email: " + loginRequest.getEmail()));

        if (!passwordEncoder.matches(loginRequest.getPassword(), user.getPassword())) {
            throw new InvalidPasswordException("Senha incorreta para o usuário: " + loginRequest.getEmail());
        }

        String token = jwtUtil.generateToken(user.getEmail());
        
        // Criar informações do perfil
        LoginResponseDTO.ProfileInfo profileInfo = new LoginResponseDTO.ProfileInfo(
            user.getProfile().getId(),
            user.getProfile().getNome(),
            user.getProfile().getStatus()
        );
        
        // Criar informações das empresas com responsabilidades
        List<LoginResponseDTO.CompanyInfo> companiesInfo = user.getCompanies().stream()
            .map(userCompany -> {
                // Verificar se o usuário é responsável por esta empresa
                boolean isResponsible = userResponsibleRepository.existsByUser_IdAndCompany_Id(
                    user.getId(), userCompany.getCompany().getId());
                
                // Buscar o cargo se for responsável
                String cargo = null;
                if (isResponsible) {
                    cargo = userResponsibleRepository.findByUser_IdAndCompany_Id(
                        user.getId(), userCompany.getCompany().getId()).getCargo();
                }
                
                return new LoginResponseDTO.CompanyInfo(
                    userCompany.getCompany().getId(),
                    userCompany.getCompany().getName_fant(),
                    userCompany.getCompany().getRazao_social(),
                    userCompany.getCompany().getCnpj(),
                    isResponsible,
                    cargo
                );
            })
            .collect(Collectors.toList());
        
        return new LoginResponseDTO(
            token,
            user.getName(),
            user.getEmail(),
            user.getUser(),
            user.getContato(),
            profileInfo,
            companiesInfo
        );
    }
}
