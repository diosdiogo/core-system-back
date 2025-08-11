package com.core.system.core_system_back.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.core.system.core_system_back.dto.LoginRequestDTO;
import com.core.system.core_system_back.dto.LoginResponseDTO;
import com.core.system.core_system_back.model.User;
import com.core.system.core_system_back.model.UserCompany;
import com.core.system.core_system_back.repository.UserRepository;
import com.core.system.core_system_back.security.JwtUtil;


@Service
public class AuthService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    public AuthService(UserRepository userRepository, PasswordEncoder passwordEncoder, JwtUtil jwtUtil) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtUtil = jwtUtil;
    }

    public LoginResponseDTO login(LoginRequestDTO loginRequest) {
        User user = userRepository.findByEmailWithRelations(loginRequest.getEmail())
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));

        if (!passwordEncoder.matches(loginRequest.getPassword(), user.getPassword())) {
            throw new RuntimeException("Senha incorreta");
        }

        String token = jwtUtil.generateToken(user.getEmail());
        
        // Criar informações do perfil
        LoginResponseDTO.ProfileInfo profileInfo = new LoginResponseDTO.ProfileInfo(
            user.getProfile().getId(),
            user.getProfile().getNome(),
            user.getProfile().getStatus()
        );
        
        // Criar informações das empresas (já carregadas pelo relacionamento)
        List<LoginResponseDTO.CompanyInfo> companiesInfo = user.getCompanies().stream()
            .map(userCompany -> new LoginResponseDTO.CompanyInfo(
                userCompany.getCompany().getId(),
                userCompany.getCompany().getName_fant(),
                userCompany.getCompany().getRazao_social(),
                userCompany.getCompany().getCnpj()
            ))
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
