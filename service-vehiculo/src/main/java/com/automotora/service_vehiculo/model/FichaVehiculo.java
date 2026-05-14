package com.automotora.service_vehiculo.model;

import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

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

    @OneToOne
    @JoinColumn(name = "vehiculo_id")
    @JsonIgnore 
    private Vehiculo vehiculo;
}
