package com.automotora.service_mantenimiento.model;

import java.time.LocalDate;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "mantenimientos")
@Data
@NoArgsConstructor
@AllArgsConstructor

public class Mantenimiento {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDate fecha;
    private String tipoServicio;
    private Double costo;

    // Solo guardo los IDs en mi DB
    private Long fichaVehiculoId;
    private Long empleadoId;

    // Datos externos (no persistidos en esta DB)
    @Transient
    private Object datosFichaVehiculo;

    @Transient
    private Object datosEmpleado;
}
