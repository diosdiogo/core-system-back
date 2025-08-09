package com.core.system.core_system_back;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Component;


@SpringBootApplication
public class CoreSystemBackApplication {

	public static void main(String[] args) {
		SpringApplication.run(CoreSystemBackApplication.class, args);
	}

	@Component
	public static class SwaggerLogger implements CommandLineRunner {
		
		@Value("${server.port:8080}")
        private int port;

		@Value("${server.servlet.context-path:}")
        private String contextPath;

		@Override
        public void run(String... args) {
            String baseUrl = "http://localhost:" + port + contextPath;
            System.out.println("---------------------------------------------------");
            System.out.println("Swagger UI disponível em: " + baseUrl + "/swagger-ui/index.html");
            System.out.println("OpenAPI JSON disponível em: " + baseUrl + "/v3/api-docs");
            System.out.println("---------------------------------------------------");
        }
	}

}
