package com.automotora.service_cliente.config;

import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.servers.Server;

@Configuration
public class OpenApiConfig {
    @Bean
    public OpenAPI ClienteOpenAPI() {
        return new OpenAPI()
            .info(new Info()
                .title("API Cliente")
                .version("v1")
                .description("Documentación del microservicio Cliente"))
            .servers(List.of(
                new Server().url("http://localhost:9002").description("Servidor local Cliente")
            ));
    }
}


