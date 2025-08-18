package com.core.system.core_system_back.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.core.system.core_system_back.dto.MenuDTO;
import com.core.system.core_system_back.model.Apps;
import com.core.system.core_system_back.model.Menu;
import com.core.system.core_system_back.repository.MenuRepository;

import jakarta.transaction.Transactional;

@Service
public class MenuService {

    private final MenuRepository menuRepository;

    public MenuService(MenuRepository menuRepository) {
        this.menuRepository = menuRepository;
    }

    @Transactional
    public List<Menu> getMenusByApp(Apps app) {
        return menuRepository.findByApp(app);
    }

    public Menu createMenu(MenuDTO menuDTO) {
        Menu menu = new Menu();
        menu.setName(menuDTO.getName());
        menu.setIcon(menuDTO.getIcon());
        menu.setUrl(menuDTO.getUrl());
        menu.setVersao(menuDTO.getVersao());
        menu.setAtivo(menuDTO.getAtivo());
        menu.setStatus(menuDTO.getStatus());
        return menuRepository.save(menu);
    }

}
