package com.core.system.core_system_back;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.Arrays;
import java.util.Optional;
import java.util.UUID;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.core.system.core_system_back.dto.LoginRequestDTO;
import com.core.system.core_system_back.dto.LoginResponseDTO;
import com.core.system.core_system_back.exception.InvalidPasswordException;
import com.core.system.core_system_back.exception.UserNotFoundException;
import com.core.system.core_system_back.model.Company;
import com.core.system.core_system_back.model.Profile;
import com.core.system.core_system_back.model.User;
import com.core.system.core_system_back.model.UserCompany;
import com.core.system.core_system_back.repository.UserRepository;
import com.core.system.core_system_back.repository.UserResponsibleRepository;
import com.core.system.core_system_back.security.JwtUtil;
import com.core.system.core_system_back.service.AuthService;
import com.core.system.core_system_back.enums.CompanyStatus;

@ExtendWith(MockitoExtension.class)
class AuthServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private JwtUtil jwtUtil;

    @Mock
    private UserResponsibleRepository userResponsibleRepository;

    @InjectMocks
    private AuthService authService;

    private User testUser;
    private Profile testProfile;
    private Company testCompany;
    private UserCompany testUserCompany;
    private LoginRequestDTO loginRequest;

    @BeforeEach
    void setUp() {
        // Criar dados de teste
        testProfile = new Profile();
        testProfile.setId(UUID.randomUUID());
        testProfile.setNome("Admin");
        testProfile.setStatus(true);

        testCompany = new Company();
        testCompany.setId(UUID.randomUUID());
        testCompany.setName_fant("Empresa Teste");
        testCompany.setRazao_social("Empresa Teste LTDA");
        testCompany.setCnpj("12345678901234");
        testCompany.setStatus(CompanyStatus.ATIVO);

        testUserCompany = new UserCompany();
        testUserCompany.setCompany(testCompany);

        testUser = new User();
        testUser.setId(UUID.randomUUID());
        testUser.setName("Usuário Teste");
        testUser.setEmail("teste@teste.com");
        testUser.setUser("usuario_teste");
        testUser.setPassword("senha_criptografada");
        testUser.setContato("11999999999");
        testUser.setCargo("Desenvolvedor");
        testUser.setProfile(testProfile);
        testUser.setCompanies(Arrays.asList(testUserCompany));

        loginRequest = new LoginRequestDTO();
        loginRequest.setEmail("teste@teste.com");
        loginRequest.setPassword("senha123");
    }

    @Test
    void testLoginSuccess() {
        // Arrange
        when(userRepository.findByEmailWithRelations("teste@teste.com"))
            .thenReturn(Optional.of(testUser));
        when(passwordEncoder.matches("senha123", "senha_criptografada"))
            .thenReturn(true);
        when(jwtUtil.generateToken("teste@teste.com"))
            .thenReturn("jwt_token_123");
        when(userResponsibleRepository.existsByUser_IdAndCompany_Id(any(), any()))
            .thenReturn(false);

        // Act
        LoginResponseDTO result = authService.login(loginRequest);

        // Assert
        assertNotNull(result);
        assertEquals("jwt_token_123", result.getToken());
        assertEquals("Usuário Teste", result.getName());
        assertEquals("teste@teste.com", result.getEmail());
        assertEquals("usuario_teste", result.getUser());
        assertEquals("11999999999", result.getContato());
        assertEquals("Desenvolvedor", result.getCargo());
        assertNotNull(result.getProfile());
        assertEquals("Admin", result.getProfile().getNome());
        assertNotNull(result.getCompanies());
        assertEquals(1, result.getCompanies().size());
        assertEquals("Empresa Teste", result.getCompanies().get(0).getName_fant());
        assertFalse(result.getCompanies().get(0).getIsResponsible());
        assertNull(result.getCompanies().get(0).getCargo());
        assertEquals(CompanyStatus.ATIVO, result.getCompanies().get(0).getStatus());
    }

    @Test
    void testLoginUserNotFound() {
        // Arrange
        when(userRepository.findByEmailWithRelations("teste@teste.com"))
            .thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(UserNotFoundException.class, () -> {
            authService.login(loginRequest);
        });
    }

    @Test
    void testLoginWrongPassword() {
        // Arrange
        when(userRepository.findByEmailWithRelations("teste@teste.com"))
            .thenReturn(Optional.of(testUser));
        when(passwordEncoder.matches("senha123", "senha_criptografada"))
            .thenReturn(false);

        // Act & Assert
        assertThrows(InvalidPasswordException.class, () -> {
            authService.login(loginRequest);
        });
    }
} 