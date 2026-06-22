package com.automotora.service_proveedor.config;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@AllArgsConstructor
@NoArgsConstructor

@Schema(description = "Formato estándar de respuesta de error")
public class ErrorResponse {

    @Schema(description = "Código de estado HTTP", example = "404")
    private int codigo;

    @Schema(description = "Mensaje de error", example = "Proveedor no encontrado")
    private String mensaje;

    @Schema(description = "Ruta del endpoint", example = "/api/v1/proveedores/99")
    private String path;

    @Schema(description = "Fecha y hora del error", example = "2026-06-21T23:30:00")
    private String timestamp;
}
