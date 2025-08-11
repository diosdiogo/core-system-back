package com.core.system.core_system_back;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.core.system.core_system_back.dto.UserResponsibleDTO;
import com.core.system.core_system_back.dto.UserResponsibleResponseDTO;
import com.core.system.core_system_back.exception.CompanyNotFoundException;
import com.core.system.core_system_back.exception.DuplicateResponsibilityException;
import com.core.system.core_system_back.exception.UserNotFoundException;
import com.core.system.core_system_back.model.Company;
import com.core.system.core_system_back.model.User;
import com.core.system.core_system_back.model.UserResponsible;
import com.core.system.core_system_back.repository.CompanyRepository;
import com.core.system.core_system_back.repository.UserRepository;
import com.core.system.core_system_back.repository.UserResponsibleRepository;
import com.core.system.core_system_back.service.UserResponsibleService;

@ExtendWith(MockitoExtension.class)
class UserResponsibleServiceTest {

    @Mock
    private UserResponsibleRepository userResponsibleRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private CompanyRepository companyRepository;

    @InjectMocks
    private UserResponsibleService userResponsibleService;

    private User testUser;
    private Company testCompany;
    private UserResponsible testUserResponsible;
    private UserResponsibleDTO testDTO;

    @BeforeEach
    void setUp() {
        testUser = new User();
        testUser.setId(UUID.randomUUID());
        testUser.setName("Usuário Teste");
        testUser.setEmail("teste@teste.com");

        testCompany = new Company();
        testCompany.setId(UUID.randomUUID());
        testCompany.setName_fant("Empresa Teste");

        testUserResponsible = new UserResponsible();
        testUserResponsible.setId(UUID.randomUUID());
        testUserResponsible.setUser(testUser);
        testUserResponsible.setCompany(testCompany);
        testUserResponsible.setCargo("Gerente de TI");

        testDTO = new UserResponsibleDTO();
        testDTO.setUserId(testUser.getId());
        testDTO.setCompanyId(testCompany.getId());
        testDTO.setCargo("Gerente de TI");
    }

    @Test
    void testCreateUserResponsibleSuccess() {
        // Arrange
        when(userRepository.findById(testUser.getId())).thenReturn(Optional.of(testUser));
        when(companyRepository.findById(testCompany.getId())).thenReturn(Optional.of(testCompany));
        when(userResponsibleRepository.existsByUser_IdAndCompany_Id(testUser.getId(), testCompany.getId()))
            .thenReturn(false);
        when(userResponsibleRepository.save(any(UserResponsible.class))).thenReturn(testUserResponsible);

        // Act
        UserResponsibleResponseDTO result = userResponsibleService.createUserResponsible(testDTO);

        // Assert
        assertNotNull(result);
        assertEquals(testUser.getId(), result.getUserId());
        assertEquals(testCompany.getId(), result.getCompanyId());
        assertEquals("Gerente de TI", result.getCargo());
    }

    @Test
    void testCreateUserResponsibleUserNotFound() {
        // Arrange
        when(userRepository.findById(testUser.getId())).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(UserNotFoundException.class, () -> {
            userResponsibleService.createUserResponsible(testDTO);
        });
    }

    @Test
    void testCreateUserResponsibleCompanyNotFound() {
        // Arrange
        when(userRepository.findById(testUser.getId())).thenReturn(Optional.of(testUser));
        when(companyRepository.findById(testCompany.getId())).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(CompanyNotFoundException.class, () -> {
            userResponsibleService.createUserResponsible(testDTO);
        });
    }

    @Test
    void testCreateUserResponsibleAlreadyExists() {
        // Arrange
        when(userRepository.findById(testUser.getId())).thenReturn(Optional.of(testUser));
        when(companyRepository.findById(testCompany.getId())).thenReturn(Optional.of(testCompany));
        when(userResponsibleRepository.existsByUser_IdAndCompany_Id(testUser.getId(), testCompany.getId()))
            .thenReturn(true);

        // Act & Assert
        assertThrows(DuplicateResponsibilityException.class, () -> {
            userResponsibleService.createUserResponsible(testDTO);
        });
    }

    @Test
    void testGetUserResponsibilities() {
        // Arrange
        when(userResponsibleRepository.findByUserIdWithRelations(testUser.getId()))
            .thenReturn(Arrays.asList(testUserResponsible));

        // Act
        List<UserResponsibleResponseDTO> result = userResponsibleService.getUserResponsibilities(testUser.getId());

        // Assert
        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(testUser.getId(), result.get(0).getUserId());
        assertEquals(testCompany.getId(), result.get(0).getCompanyId());
    }

    @Test
    void testIsUserResponsibleForCompany() {
        // Arrange
        when(userResponsibleRepository.existsByUser_IdAndCompany_Id(testUser.getId(), testCompany.getId()))
            .thenReturn(true);

        // Act
        boolean result = userResponsibleService.isUserResponsibleForCompany(testUser.getId(), testCompany.getId());

        // Assert
        assertTrue(result);
    }
} 