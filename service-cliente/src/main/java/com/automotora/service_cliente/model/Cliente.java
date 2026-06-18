package com.automotora.service_cliente.model;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "Entidad que representa a un cliente de la automotora")
public class Cliente {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "Identificador único del cliente", example = "1")
    private Long id;

    @Schema(description = "Nombre completo del cliente", example = "Juan Pérez")
    private String nombre;

    @Schema(description = "RUT del cliente", example = "12345678-9")
    private String rut;

    @Schema(description = "Teléfono de contacto (9 dígitos)", example = "987654321")
    private String telefono;

    @Schema(description = "Correo electrónico del cliente", example = "juan.perez@mail.com")
    private String email;

    @Schema(description = "Historial de compras realizadas por el cliente", example = "Compra de vehículo Toyota Corolla 2022")
    private String historialCompras;
}