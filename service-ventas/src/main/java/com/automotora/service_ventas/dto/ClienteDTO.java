package com.automotora.service_ventas.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import io.swagger.v3.oas.annotations.media.Schema;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "Datos simplificados del cliente para pruebas y respuestas")
public class ClienteDTO {

    @Schema(description = "Identificador único del cliente", example = "1")
    private Long id;

    @Schema(description = "Nombre completo del cliente", example = "Juan Pérez")
    private String nombre;

    @Schema(description = "RUT del cliente", example = "12345678-9")
    private String rut;

    @Schema(description = "Teléfono de contacto", example = "987654321")
    private String telefono;

    @Schema(description = "Correo electrónico del cliente", example = "juan.perez@mail.com")
    private String email;
}
