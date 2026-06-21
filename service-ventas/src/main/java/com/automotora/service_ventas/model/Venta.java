package com.automotora.service_ventas.model;

import java.time.LocalDate;

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
public class Venta {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDate fecha;

    @Schema(description = "Monto total de la venta")
    private Double monto;

    @Schema(description = "ID del cliente (Referencia al microservicio de clientes)")
    private Long clienteId;

    @Schema(description = "ID de la ficha del vehículo vendido (Referencia al microservicio de fichas)")
    private Long fichaId;

    @Transient
    @Schema(description = "Datos detallados del cliente. Se cargan en tiempo de ejecución vía WebClient", 
            accessMode = Schema.AccessMode.READ_ONLY)
    private Object datosCliente;

    @Transient
    @Schema(description = "Datos detallados de la ficha del vehículo. Se cargan en tiempo de ejecución vía WebClient", 
            accessMode = Schema.AccessMode.READ_ONLY)
    private Object datosFichaVehiculo;
}