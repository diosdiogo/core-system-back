package com.core.system.core_system_back.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.core.system.core_system_back.model.Apps;
import com.core.system.core_system_back.model.Menu;
import java.util.List;


@Repository
public interface MenuRepository extends JpaRepository<Menu, UUID> {
    List<Menu> findByApp(Apps app);
}
