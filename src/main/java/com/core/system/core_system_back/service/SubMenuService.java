package com.core.system.core_system_back.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.core.system.core_system_back.model.Menu;
import com.core.system.core_system_back.model.SubMenu;
import com.core.system.core_system_back.repository.SubMenuRepository;

import jakarta.transaction.Transactional;

@Service
public class SubMenuService {

    private final SubMenuRepository subMenuRepository;

    public SubMenuService(SubMenuRepository subMenuRepository) {
        this.subMenuRepository = subMenuRepository;
    }

    @Transactional
    public List<SubMenu> getSubMenusByMenu(Menu menu) {
        return subMenuRepository.findByMenu(menu);
    }
}
