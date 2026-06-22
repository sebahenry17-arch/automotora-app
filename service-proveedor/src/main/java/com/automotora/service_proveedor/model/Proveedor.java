package com.automotora.service_proveedor.model;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@Table(name = "proveedores")
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Schema(description = "Entidad que representa a un proveedor de repuestos o servicios")
public class Proveedor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "Identificador único del proveedor", example = "1")
    private Long id;

    @Schema(description = "Nombre del proveedor", example = "Repuestos S.A.")
    private String nombre;

    @Schema(description = "RUT o DNI del proveedor", example = "12.345.678-9")
    private String rutDni;

    @Schema(description = "Teléfono de contacto", example = "+56 9 1234 5678")
    private String telefono;

    @Schema(description = "Correo electrónico del proveedor", example = "contacto@repuestos.cl")
    private String email;

    @Schema(description = "Dirección física del proveedor", example = "Av. Siempre Viva 123, Santiago")
    private String direccion;
}