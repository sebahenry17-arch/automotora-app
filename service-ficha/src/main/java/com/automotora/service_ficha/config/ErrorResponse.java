package com.automotora.service_ficha.config;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import io.swagger.v3.oas.annotations.media.Schema;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "Modelo de respuesta de error estándar")
public class ErrorResponse {

    @Schema(description = "Código de error interno o HTTP", example = "404")
    private int codigo;

    @Schema(description = "Mensaje descriptivo del error", example = "Ficha con ID 99 no existe")
    private String mensaje;

    @Schema(description = "Ruta del endpoint donde ocurrió el error", example = "/api/v1/fichas/99")
    private String path;

    @Schema(description = "Marca de tiempo del error", example = "2026-06-19T23:00:00")
    private String timestamp;
}
