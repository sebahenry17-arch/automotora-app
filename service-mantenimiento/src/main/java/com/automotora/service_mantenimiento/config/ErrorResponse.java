package com.automotora.service_mantenimiento.config;

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

    @Schema(description = "Mensaje descriptivo del error", example = "Mantenimiento con ID 99 no existe")
    private String mensaje;

    @Schema(description = "Ruta del endpoint donde ocurrió el error", example = "/api/v1/mantenimientos/99")
    private String path;

    @Schema(description = "Marca de tiempo del error", example = "2026-06-21T17:50:00")
    private String timestamp;
}