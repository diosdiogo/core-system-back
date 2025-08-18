package com.core.system.core_system_back.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.core.system.core_system_back.model.Menu;
import com.core.system.core_system_back.model.SubMenu;
import com.core.system.core_system_back.service.SubMenuService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/api/sub-menu")
@Tag(name = "Menu", description = "SubMenu do app")
public class SubMenuController {

    private final SubMenuService subMenuService;

    public SubMenuController(SubMenuService subMenuService) {
        this.subMenuService = subMenuService;
    }

    @GetMapping("/{menu}")
    @Operation(summary = "Buscar submenus por menu", description = "Busca os submenus por menu")
    public List<SubMenu> getSubMenusByMenu(@PathVariable Menu menu) {
        return subMenuService.getSubMenusByMenu(menu);
    }
}
