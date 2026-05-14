package com.automotora.service_vehiculo.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "tipo_vehiculo")
@Data
@AllArgsConstructor
@NoArgsConstructor

public class TipoVehiculo 
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nombre; // Ejemplo: "Sedán", "SUV", "Camioneta", etc.

}