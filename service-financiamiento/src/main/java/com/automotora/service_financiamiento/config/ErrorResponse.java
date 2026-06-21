package com.automotora.service_financiamiento.config;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "Modelo de error estándar para todas las respuestas")
public class ErrorResponse {

    @Schema(description = "Código HTTP del error", example = "404")
    private int codigo;

    @Schema(description = "Mensaje descriptivo del error", example = "Financiamiento no encontrado con ID: 7")
    private String mensaje;

    @Schema(description = "Ruta del endpoint donde ocurrió el error", example = "/api/v1/financiamientos/7")
    private String path;

    @Schema(description = "Fecha y hora del error", example = "2026-06-21T15:32:00")
    private String timestamp;
}
