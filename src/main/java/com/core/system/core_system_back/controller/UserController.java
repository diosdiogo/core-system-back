package com.core.system.core_system_back.controller;

import com.core.system.core_system_back.dto.UserDTO;
import com.core.system.core_system_back.model.User;
import com.core.system.core_system_back.service.UserService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
@Tag(name = "User", description = "Controle de usuário" )
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping
    @Operation(summary = "Criar usuário", description = "Cadatrar novo usuário do sistema")
    public ResponseEntity<User> createUser(@Valid @RequestBody UserDTO dto) {
        User createdUser = userService.createUser(dto);
        return ResponseEntity.ok(createdUser);
    }
}
