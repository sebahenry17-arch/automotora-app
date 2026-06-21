package com.automotora.service_ficha.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class VehiculoDTO {
    private Long id;
    private String marca;
    private String modelo;
    private int año;
    private int stock;
    private String tipoVehiculo; // solo el nombre del tipo, no toda la entidad
}