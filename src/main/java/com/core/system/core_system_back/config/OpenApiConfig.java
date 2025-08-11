package com.core.system.core_system_back.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.tags.Tag;
import org.springdoc.core.customizers.OpenApiCustomiser;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@OpenAPIDefinition(
    info = @Info(title = "Core System API", version = "v1"),
    security = {
        @SecurityRequirement(name = "bearerAuth") // Aplica o token globalmente
    }
)
@SecurityScheme(
    name = "bearerAuth",
    type = SecuritySchemeType.HTTP,
    scheme = "bearer",
    bearerFormat = "JWT"
)
public class OpenApiConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
            .addTagsItem(new io.swagger.v3.oas.models.tags.Tag().name("Auth").description("Operações de autenticação"))
            .addTagsItem(new io.swagger.v3.oas.models.tags.Tag().name("User").description("Controle de usuário"))
            .addTagsItem(new io.swagger.v3.oas.models.tags.Tag().name("Profile").description("Controle de perfis"))
            .addTagsItem(new io.swagger.v3.oas.models.tags.Tag().name("Company").description("Controle de empresas"))
            .addTagsItem(new io.swagger.v3.oas.models.tags.Tag().name("Apps").description("Controle de aplicações"))
            .addTagsItem(new io.swagger.v3.oas.models.tags.Tag().name("Hello").description("Operações de exemplo"));
    }

    @Bean
    public OpenApiCustomiser sortTagsCustomiser() {
        return openApi -> {
            List<String> order = Arrays.asList("Auth", "User", "Profile", "Company", "Apps", "Hello");

            if (openApi.getTags() != null) {
                LinkedHashMap<String, Tag> tagsMap = new LinkedHashMap<>();
                for (Tag tag : openApi.getTags()) {
                    tagsMap.put(tag.getName(), tag);
                }
                List<Tag> sortedTags = order.stream()
                        .map(tagsMap::get)
                        .filter(tag -> tag != null)
                        .collect(Collectors.toList());
                openApi.setTags(sortedTags);
            }
        };
    }
}