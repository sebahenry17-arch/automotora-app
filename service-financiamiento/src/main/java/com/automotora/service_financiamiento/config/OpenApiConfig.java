package com.automotora.service_financiamiento.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI financiamientoOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("API Financiamiento")
                        .version("v1")
                        .description("Documentación del microservicio Financiamiento"))
                .servers(List.of(
                        new Server().url("http://localhost:9005").description("Servidor local Financiamiento")
                ));
    }
}
