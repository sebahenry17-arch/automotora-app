package com.automotora.service_inventario.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Transient;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Repuesto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Schema(description = "Nombre del repuesto")
    private String nombre;

    @Schema(description = "Descripción detallada del repuesto")
    private String descripcion;

    @Schema(description = "Cantidad disponible en inventario")
    private Integer cantidadDisponible;

    @Schema(description = "Ubicación física dentro del inventario")
    private String ubicacion;

    @Schema(description = "ID del proveedor (Referencia al microservicio de proveedores)")
    private Long proveedorId;

    @Transient
    @Schema(description = "Datos detallados del proveedor. Se cargan en tiempo de ejecución vía WebClient",
            accessMode = Schema.AccessMode.READ_ONLY)
    private Object datosProveedor;
}