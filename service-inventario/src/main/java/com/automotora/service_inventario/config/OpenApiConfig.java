package com.automotora.service_inventario.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.servers.Server;

import java.util.List;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI InventarioOpenAPI() {
        return new OpenAPI()
            .info(new Info()
                .title("API Inventario")
                .version("v1")
                .description("Documentación del microservicio Inventario"))
            .servers(List.of(
                new Server().url("http://localhost:9009").description("Servidor local Inventario")
            ));
    }
}