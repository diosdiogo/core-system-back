package com.core.system.core_system_back.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.core.system.core_system_back.model.SubMenu;
import com.core.system.core_system_back.model.Menu;

@Repository
public interface SubMenuRepository extends JpaRepository<SubMenu, UUID> {
    List<SubMenu> findByMenu(Menu menu);
}
