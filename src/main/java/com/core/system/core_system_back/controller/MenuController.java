package com.core.system.core_system_back.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.core.system.core_system_back.dto.MenuDTO;
import com.core.system.core_system_back.model.Apps;
import com.core.system.core_system_back.model.Menu;
import com.core.system.core_system_back.service.MenuService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/api/menu")
@Tag(name = "Menu", description = "Menu do app")
public class MenuController {

    private final MenuService menuService;

    public MenuController(MenuService menuService) {
        this.menuService = menuService;
    }

    @GetMapping
    @Operation(summary = "Buscar menus por app", description = "Busca os menus por app")
    public List<Menu> getMenusByApp(@PathVariable Apps app) {
        return menuService.getMenusByApp(app);
    }

    @PostMapping
    @Operation(summary = "Criar menu", description = "Cria um novo menu")
    public Menu createMenu(@RequestBody MenuDTO menuDTO) {
        return menuService.createMenu(menuDTO);
    }

}
