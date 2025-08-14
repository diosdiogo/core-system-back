package com.core.system.core_system_back.repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.core.system.core_system_back.model.AppCompany;

@Repository
public interface AppCompanyRepository extends JpaRepository<AppCompany, UUID> {
    
    // Buscar por empresa
    List<AppCompany> findByCompany_Id(UUID companyId);
    
    // Buscar por aplicação
    List<AppCompany> findByApp_Id(UUID appId);
    
    // Buscar por empresa e aplicação específicos
    Optional<AppCompany> findByCompany_IdAndApp_Id(UUID companyId, UUID appId);
    
    // Verificar se existe relacionamento
    boolean existsByCompany_IdAndApp_Id(UUID companyId, UUID appId);
    
    // Buscar por empresa com relacionamentos carregados
    @Query("SELECT ae FROM AppCompany ae " +
           "LEFT JOIN FETCH ae.app " +
           "LEFT JOIN FETCH ae.company " +
           "WHERE ae.company.id = :companyId")
    List<AppCompany> findByCompanyIdWithRelations(@Param("companyId") UUID companyId);
    
    // Buscar por aplicação com relacionamentos carregados
    @Query("SELECT ae FROM AppCompany ae " +
           "LEFT JOIN FETCH ae.app " +
           "LEFT JOIN FETCH ae.company " +
           "WHERE ae.app.id = :appId")
    List<AppCompany> findByAppIdWithRelations(@Param("appId") UUID appId);
    
    // Buscar todos com relacionamentos carregados
    @Query("SELECT ae FROM AppCompany ae " +
           "LEFT JOIN FETCH ae.app " +
           "LEFT JOIN FETCH ae.company")
    List<AppCompany> findAllWithRelations();
} 