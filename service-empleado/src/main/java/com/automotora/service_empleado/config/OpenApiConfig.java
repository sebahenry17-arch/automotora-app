package com.automotora.service_empleado.config;

import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.servers.Server;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI empleadoOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("API Empleado")
                        .version("v1")
                        .description("Documentación del microservicio Empleado"))
                .servers(List.of(
                        new Server().url("http://localhost:9006").description("Servidor local Empleado")
                ));
    }
}