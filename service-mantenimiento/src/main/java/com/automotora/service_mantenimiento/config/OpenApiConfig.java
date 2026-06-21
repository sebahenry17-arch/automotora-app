package com.automotora.service_mantenimiento.config;

import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.servers.Server;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI MantenimientoOpenAPI() {
        return new OpenAPI()
            .info(new Info()
                .title("API Mantenimiento")
                .version("v1")
                .description("Documentación del microservicio Mantenimiento"))
            .servers(List.of(
                new Server().url("http://localhost:9007").description("Servidor local Mantenimiento")
            ));
    }
}