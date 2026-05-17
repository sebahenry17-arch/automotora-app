package com.automotora.service_ficha.model;

import java.time.LocalDate;

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
public class FichaVehiculo 
{
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
}
