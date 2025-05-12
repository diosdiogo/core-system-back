package com.core.system.core_system_back.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/api/hello")
@Tag(name = "Hello", description = "Operações de exemplo")
public class HelloController {
    
    @GetMapping
    @Operation(summary = "Obter exemplo", description = "Retorna um exemplo de resposta")
    public String hello() {
        return "hello";
    }
}
