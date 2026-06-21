package com.automotora.service_ficha.model;

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

import com.automotora.service_ficha.dto.VehiculoDTO;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class FichaVehiculo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String numeroSerie;
    private String color;
    private String combustible;
    private String transmision;
    private LocalDate fechaMatricula;
    private Double precio;
    private Boolean vendida;
    private Long vehiculoId;

    @Transient
    @Schema(description = "Datos detallados del vehículo. Se cargan en tiempo de ejecución vía WebClient", accessMode = Schema.AccessMode.READ_ONLY)
    private VehiculoDTO datosVehiculo;
}
