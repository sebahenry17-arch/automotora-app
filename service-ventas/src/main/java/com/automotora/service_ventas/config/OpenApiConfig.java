package com.automotora.service_ventas.config;



import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.servers.Server;

import java.util.List;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI VentasOpenAPI() {
        return new OpenAPI()
            .info(new Info()
                .title("API Ventas")
                .version("v1")
                .description("Documentación del microservicio Ventas"))
            .servers(List.of(
                new Server().url("http://localhost:9004").description("Servidor local Ventas")
            ));
    }
}
