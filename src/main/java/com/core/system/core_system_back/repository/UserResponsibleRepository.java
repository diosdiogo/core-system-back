package com.core.system.core_system_back.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.core.system.core_system_back.model.UserResponsible;

public interface UserResponsibleRepository extends JpaRepository<UserResponsible, UUID> {
    
    // Buscar todas as responsabilidades de um usuário
    List<UserResponsible> findByUser_Id(UUID userId);
    
    // Buscar todas as responsabilidades de uma empresa
    List<UserResponsible> findByCompany_Id(UUID companyId);
    
    // Buscar responsabilidade específica de um usuário em uma empresa
    UserResponsible findByUser_IdAndCompany_Id(UUID userId, UUID companyId);
    
    // Verificar se um usuário é responsável por uma empresa específica
    boolean existsByUser_IdAndCompany_Id(UUID userId, UUID companyId);
    
    // Buscar responsabilidades com relacionamentos carregados
    @Query("SELECT ur FROM UserResponsible ur " +
           "LEFT JOIN FETCH ur.user " +
           "LEFT JOIN FETCH ur.company " +
           "WHERE ur.user.id = :userId")
    List<UserResponsible> findByUserIdWithRelations(@Param("userId") UUID userId);
} 