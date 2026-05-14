package com.automotora.service_vehiculo.model;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Vehiculo 
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String marca;
    private String modelo;
    private int año;
    private double precio;
    private String estado;
    private int stock;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_tipo_vehiculo")
    private TipoVehiculo tipoVehiculo;

    @OneToOne(mappedBy = "vehiculo", cascade = CascadeType.ALL)
    private FichaVehiculo ficha;    
}
